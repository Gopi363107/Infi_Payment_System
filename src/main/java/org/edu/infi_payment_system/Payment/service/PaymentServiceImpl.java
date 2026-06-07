package org.edu.infi_payment_system.Payment.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Account.entity.Accounts;
import org.edu.infi_payment_system.Account.repository.AccountRepository;
import org.edu.infi_payment_system.Audit.enums.AuditAction;
import org.edu.infi_payment_system.Audit.service.AuditService;
import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.FraudCheck.service.FraudService;
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

    @Override
    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequest){

        // 1.fetch sender account
        Accounts sender  = (Accounts) accountRepository
                .findByAccountId(paymentRequest.getSenderAccountId())
                .orElseThrow(()->new AccountNotFoundException("sender account not found"));

        // 2.fetch receiver account
        Accounts receiver = (Accounts) accountRepository
                .findByAccountId(paymentRequest.getReceiverAccountId())
                .orElseThrow(() -> new AccountNotFoundException("receiver account not found"));

        if(sender.getAccountId().equals(receiver.getAccountId())){
            throw new InvalidPaymentException("Sender and receiver cannot be same.");
        }

        // 3. idempotency key check
        Payments existing = paymentRepository
                .findByIdempotencyKey(paymentRequest.getIdempotencyKey());

        if(existing != null){
            return paymentMapper.toResponseDto(existing);
        }

        // 4. create  payment entity (pending)
        Payments payment = paymentMapper.toEntity(paymentRequest);
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);

        auditService.saveAudit(
                payment.getPaymentId(),
                AuditAction.PAYMENT_INITIATED,
                paymentRequest.getSenderAccountId(),
                paymentRequest.getReceiverAccountId()
        );

        // 5. Fraud detection check using Rules
        FraudCheckResult fraudCheckResult = fraudService.checkResult(paymentRequest);

        if(fraudCheckResult.isFraudulent()){
            auditService.saveAudit(
                    payment.getPaymentId(),
                    AuditAction.FRAUD_DETECTED,
                    paymentRequest.getSenderAccountId(),
                    paymentRequest.getReceiverAccountId()
            );
            throw new RuntimeException(
                    fraudCheckResult.getReason()
            );
        }

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
        }
        catch(Exception e){

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
                paymentRepository.getPaymentId(),
                AuditAction.PAYMENT_COMPLETED,
                paymentRequest.getSenderAccountId(),
                paymentRequest.getReceiverAccountId()
        );
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
