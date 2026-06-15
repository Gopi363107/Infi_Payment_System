package org.edu.infi_payment_system.Payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Account.entity.Accounts;
import org.edu.infi_payment_system.Account.repository.AccountRepository;
import org.edu.infi_payment_system.Audit.enums.AuditAction;
import org.edu.infi_payment_system.Audit.service.AuditService;
import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.FraudCheck.service.FraudService;
import org.edu.infi_payment_system.Notification.event.PaymentCompletedEvent;
import org.edu.infi_payment_system.Notification.event.Publisher.EventPublisher;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.entity.Payments;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.edu.infi_payment_system.Payment.exception.custom.*;
import org.edu.infi_payment_system.Payment.mapper.PaymentMapper;
import org.edu.infi_payment_system.Payment.mapper.TransactionRequestMapper;
import org.edu.infi_payment_system.Payment.repository.PaymentRepository;
import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.edu.infi_payment_system.Transaction.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final PaymentMapper paymentMapper;
    private final TransactionRequestMapper transactionRequestMapper;
    private final FraudService fraudService;
    private final AuditService auditService;
    private final EventPublisher eventPublisher;


    @Override
    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequest){

        log.info("Payment started");

        // 1.fetch sender account
        Accounts sender  = (Accounts) accountRepository
                .findByAccountId(paymentRequest.getSenderAccountId())
                .orElseThrow(()->new AccountNotFoundException("sender account not found"));

        // 2.fetch receiver account
        Accounts receiver = (Accounts) accountRepository
                .findByAccountId(paymentRequest.getReceiverAccountId())
                .orElseThrow(() -> new AccountNotFoundException("receiver account not found"));

        if(sender.getAccountId().equals(receiver.getAccountId())){
            log.error("Sender and Receiver ID is same");
            throw new InvalidPaymentException("Sender and receiver cannot be same.");
        }

        log.info("Sender and Receiver ID check completed");

        // 3. idempotency key check
        Payments existing = paymentRepository
                .findByIdempotencyKey(paymentRequest.getIdempotencyKey());

        if(existing != null){
            log.error("Same Idempotency is detected");
            return paymentMapper.toResponseDto(existing);
        }

        log.info("Duplicate payment is checked!");

        // 4. create  payment entity (pending)
        Payments payment = paymentMapper.toEntity(paymentRequest);
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);

        log.info("Payment initialised!");

        auditService.saveAudit(
                payment.getPaymentId(),
                AuditAction.PAYMENT_INITIATED,
                paymentRequest.getSenderAccountId(),
                paymentRequest.getReceiverAccountId()
        );

        log.info("Fraud Check Started");

        // 5. Fraud detection check using Rules
        FraudCheckResult fraudCheckResult = fraudService.checkResult(paymentRequest);

        if(fraudCheckResult.isFraudulent()){
            auditService.saveAudit(
                    payment.getPaymentId(),
                    AuditAction.FRAUD_DETECTED,
                    paymentRequest.getSenderAccountId(),
                    paymentRequest.getReceiverAccountId()
            );
            log.error("Fraud payment request detected!");
            throw new RuntimeException(
                    fraudCheckResult.getReason()
            );
        }

        log.info("Fraud check passed!");

        log.info("Money transaction is started!");
        // 6.create a transaction request to process payment
        TransactionRequestDto transactionRequest = transactionRequestMapper.toRequest(
                payment,
                paymentRequest);


        try{
            auditService.saveAudit(
                    payment.getPaymentId(),
                    AuditAction.PAYMENT_PENDING,
                    paymentRequest.getSenderAccountId(),
                    paymentRequest.getReceiverAccountId()
            );

            log.info("Money transaction is processing!");

            transactionService.processTransaction(transactionRequest);

            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setCompletedAt(LocalDateTime.now());

            auditService.saveAudit(
                    payment.getPaymentId(),
                    AuditAction.PAYMENT_SUCCESS,
                    paymentRequest.getSenderAccountId(),
                    paymentRequest.getReceiverAccountId()
            );

            paymentRepository.save(payment);

            log.info("Payment success!");
        }
        catch(Exception e){

            log.error("Payment Failed!");

            // 9. Mark Payment FAILED
            payment.setStatus(PaymentStatus.FAILED);
            payment.setCompletedAt(LocalDateTime.now());

            auditService.saveAudit(
                    payment.getPaymentId(),
                    AuditAction.PAYMENT_FAILED,
                    paymentRequest.getSenderAccountId(),
                    paymentRequest.getReceiverAccountId()
            );
            // 10. save failed transaction
            paymentRepository.save(payment);

            throw e;
        }
        auditService.saveAudit(
                payment.getPaymentId(),
                AuditAction.PAYMENT_COMPLETED,
                paymentRequest.getSenderAccountId(),
                paymentRequest.getReceiverAccountId()
        );
        log.info("Payment process is completed!");

        PaymentCompletedEvent event = PaymentCompletedEvent.builder()
                .paymentId(payment.getPaymentId())
                .senderId(
                        payment.getSenderAccountId()
                )
                .receiverId(
                        payment.getReceiverAccountId()
                )
                .amount(payment.getAmount())
                .status(PaymentStatus.SUCCESS)
                .build();

        eventPublisher.publishPaymentCompletedEvent(event);

        return paymentMapper.toResponseDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentById(UUID id){

        Payments payment = paymentRepository.findByPaymentId(id)
                .orElseThrow(() -> new PaymentIdNotFoundException("Payment ID not found"));

        return paymentMapper.toResponseDto(payment);
    }

    public List<PaymentResponseDto> getPaymentByAccount(UUID accountId){
        return paymentRepository.findBySenderAccountIdOrReceiverAccountId
                        (accountId, accountId)
                .stream()
                .map(paymentMapper :: toResponseDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getAllPayments(){
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper  :: toResponseDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getPaymentByStatus(PaymentStatus status){
        return paymentRepository.findByStatus(status)
                .stream()
                .map(paymentMapper :: toResponseDto)
                .toList();
    }
}
