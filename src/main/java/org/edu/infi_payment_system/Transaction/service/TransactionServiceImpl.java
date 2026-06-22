package org.edu.infi_payment_system.Transaction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Account.entity.Accounts;
import org.edu.infi_payment_system.Account.repository.AccountRepository;
import org.edu.infi_payment_system.Audit.enums.AuditAction;
import org.edu.infi_payment_system.Audit.service.AuditService;
import org.edu.infi_payment_system.Cache.service.AccountCacheService;
import org.edu.infi_payment_system.Cache.service.TransactionCacheService;
import org.edu.infi_payment_system.Ledger.service.LedgerService;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.edu.infi_payment_system.Notification.enums.ReferenceType;
import org.edu.infi_payment_system.Notification.event.NotificationEvent;
import org.edu.infi_payment_system.Notification.kafka.NotificationProducer;
import org.edu.infi_payment_system.Payment.exception.custom.AccountNotFoundException;
import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;
import org.edu.infi_payment_system.Transaction.entity.Transactions;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.edu.infi_payment_system.Transaction.exception.custom.InsufficientBalanceException;
import org.edu.infi_payment_system.Transaction.exception.custom.PaymentIdNotFoundException;
import org.edu.infi_payment_system.Transaction.exception.custom.TransactionIdNotFoundException;
import org.edu.infi_payment_system.Transaction.mapper.TransactionMapper;
import org.edu.infi_payment_system.Transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final LedgerService ledgerService;
    private final TransactionMapper transactionMapper;
    private final AuditService auditService;
    private final AccountCacheService accountCacheService;
    private final TransactionCacheService transactionCacheService;
    private final NotificationProducer notificationProducer;

    @Override
    @Transactional
    public void processTransaction(TransactionRequestDto transactionRequest) {


        auditService.saveAudit(
                transactionRequest.getPaymentId(),
                AuditAction.TRANSACTION_CREATED,
                transactionRequest.getSenderId(),
                transactionRequest.getReceiverId()
        );

        // 1. get senderId and receiverId from TransactionRequestDto
        UUID senderId = transactionRequest.getSenderId();
        UUID receiverId = transactionRequest.getReceiverId();

        log.info("Sender and receiver id locked!");
        // 2. if sender and receiver try to transfer payment same
        // time so we have to find which process do first
        UUID firstLockId =
                senderId.compareTo(receiverId) < 0 ? senderId : receiverId;

        UUID secondLockId =
                senderId.compareTo(receiverId) < 0 ? receiverId : senderId;

        // 3. Lock both sender , receiver accounts to prevent duplicate payment transaction
        Accounts firstAccount = accountRepository
                .findByAccountIdForUpdate(firstLockId)
                        .orElseThrow(()-> new AccountNotFoundException(
                                "Account not found :" + firstLockId
                        ));

        Accounts secondAccount = accountRepository
                .findByAccountIdForUpdate(secondLockId)
                        .orElseThrow(()-> new AccountNotFoundException(
                                "Account not found :" + secondLockId
                        ));

        auditService.saveAudit(
                transactionRequest.getPaymentId(),
                AuditAction.ACCOUNT_LOCKED,
                senderId,
                receiverId
        );

        log.info("Deadlock is prevented!");
        Accounts sender = firstAccount.getAccountId().equals(senderId)
                ? firstAccount : secondAccount ;

        Accounts receiver = firstAccount.getAccountId().equals(senderId)
                ? secondAccount : firstAccount ;

        log.info("Balance checking is initiated!");
        // 4. check the sender balance if not enough balance then throw error
        if(sender.getBalance().compareTo(transactionRequest.getAmount()) < 0){
            auditService.saveAudit(
                    transactionRequest.getPaymentId(),
                    AuditAction.INSUFFICIENT_BALANCE,
                    transactionRequest.getSenderId(),
                    transactionRequest.getReceiverId()
            );
            log.error("Insufficient balance!");
            throw new InsufficientBalanceException(
                    "Insufficient balance!"
            );
        }

        // 5. set the remaining balance in sender after debited the amount
        sender.setBalance(
                sender.getBalance().subtract(transactionRequest.getAmount())
        );

        log.info("Balance deducted!");
        auditService.saveAudit(
                transactionRequest.getPaymentId(),
                AuditAction.DEBIT_SUCCESS,
                transactionRequest.getSenderId(),
                transactionRequest.getReceiverId()
        );

        // 6. set the total amount as balance after add the amount from sender to receiver
        receiver.setBalance(
                receiver.getBalance().add(transactionRequest.getAmount())
        );

        log.info("Balance credited!");
        auditService.saveAudit(
                transactionRequest.getPaymentId(),
                AuditAction.CREDIT_SUCCESS,
                transactionRequest.getSenderId(),
                transactionRequest.getReceiverId()
        );

        log.info("Transaction is saved!");
        // 7. save the sender and receiver account in DB
        accountRepository.save(sender);
        accountRepository.save(receiver);

        accountCacheService.save(sender);
        accountCacheService.save(receiver);

        // 8. create a ledger entry to store the amount balance in both sender and receiver account
        ledgerService.createDoubleEntryLedger(
                transactionRequest.getPaymentId(),
                sender.getAccountId(),
                receiver.getAccountId(),
                transactionRequest.getAmount()
        );
        log.info("Ledger entry created!");
        auditService.saveAudit(
                transactionRequest.getPaymentId(),
                AuditAction.LEDGER_ENTRY_COMPLETED,
                transactionRequest.getSenderId(),
                transactionRequest.getReceiverId()
        );

        log.info("Auditing completed!");
        // 9. save the transaction in the transaction repository
        Transactions transaction = Transactions.builder()
                .paymentId(transactionRequest.getPaymentId())
                .senderId(transactionRequest.getSenderId())
                .receiverId(transactionRequest.getReceiverId())
                .amount(transactionRequest.getAmount())
                .transactionStatus(TransactionStatus.SUCCESS)
                .build();

        Transactions savedTransaction =  transactionRepository.save(transaction);

        transactionCacheService.save(
                savedTransaction.getTransactionId(),
                savedTransaction.getTransactionStatus()
        );

        auditService.saveAudit(
                transactionRequest.getPaymentId(),
                AuditAction.TRANSACTION_SUCCESS,
                transactionRequest.getSenderId(),
                transactionRequest.getReceiverId()
        );
        log.info("Transaction success!");

        notificationProducer.publish(

                NotificationEvent.builder()
                        .paymentId(transactionRequest.getPaymentId())
                        .userId(sender.getUser().getId())
                        .title("Payment Successful")
                        .message(
                                "Payment of ₹" +
                                        transactionRequest.getAmount() +
                                        " completed successfully"
                        )
                        .notificationType(NotificationType.EMAIL)
                        .referenceType(ReferenceType.PAYMENT_SUCCESS)
                        .build()
        );

    }

    public Accounts getAccount(UUID id) {

        Accounts cached =
                accountCacheService.get(id);

        if (cached != null) {
            return cached;
        }

        Accounts account =
                accountRepository.findById(id)
                        .orElseThrow();

        accountCacheService.save(account);

        return account;
    }

    public TransactionStatus getTransactionStatus(UUID transactionId){

        TransactionStatus cached = transactionCacheService.get(transactionId);

        if(cached != null){
            return cached;
        }

        Transactions transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow();

        transactionCacheService.save(
                transaction.getTransactionId(),
                transaction.getTransactionStatus()
        );

        return transaction.getTransactionStatus();

    }

    @Override
    public TransactionResponseDto getByPaymentId(UUID paymentId) {

        Transactions transaction =  transactionRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentIdNotFoundException(
                        "Payment Id not found"
                        )
                );

        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    public TransactionResponseDto getByTransactionId(UUID transactionId) {
        Transactions transaction =  transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionIdNotFoundException(
                                "Transaction Id not found"
                        )
                );

        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    public List<TransactionResponseDto> getAllTransaction() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper :: toResponseDto)
                .toList();
    }

    @Override
    public List<TransactionResponseDto> getByTransactionStatus(TransactionStatus status) {
        return transactionRepository.findByTransactionStatus(status)
                .stream()
                .map(transactionMapper :: toResponseDto)
                .toList();
    }
}
