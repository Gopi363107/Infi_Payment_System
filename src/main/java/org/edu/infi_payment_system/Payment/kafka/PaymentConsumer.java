package org.edu.infi_payment_system.Payment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Audit.enums.AuditAction;
import org.edu.infi_payment_system.Audit.service.AuditService;
import org.edu.infi_payment_system.Payment.entity.Payments;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.edu.infi_payment_system.Payment.event.PaymentProcessingEvent;
import org.edu.infi_payment_system.Payment.repository.PaymentRepository;
import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.edu.infi_payment_system.Transaction.mapper.TransactionMapper;
import org.edu.infi_payment_system.Transaction.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentRepository paymentRepository;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final AuditService auditService;

    @KafkaListener(
            topics = "payment-processing-topic",
            groupId = "payment-group"
    )
    public void consume(PaymentProcessingEvent event){
        Payments payment = paymentRepository
                .findByPaymentId(
                        event.getPaymentId()
                )
                .orElseThrow();

        try{

            TransactionRequestDto request = TransactionRequestDto.builder()
                    .paymentId(
                            event.getPaymentId()
                    )
                    .senderId(
                            event.getSenderId()
                    )
                    .receiverId(
                            event.getReceiverId()
                    )
                    .amount(
                            event.getAmount()
                    )
                    .build();

            payment.setStatus(
                    PaymentStatus.SUCCESS
            );

            payment.setCompletedAt(
                    LocalDateTime.now()
            );

            paymentRepository.save(payment);

            auditService.saveAudit(
                    payment.getPaymentId(),
                    AuditAction.PAYMENT_SUCCESS,
                    payment.getSenderAccountId(),
                    payment.getReceiverAccountId()
            );
        } catch (Exception e) {

            payment.setStatus(
                    PaymentStatus.FAILED
            );

            payment.setCompletedAt(
                    LocalDateTime.now()
            );

            auditService.saveAudit(
                    payment.getPaymentId(),
                    AuditAction.PAYMENT_FAILED,
                    payment.getSenderAccountId(),
                    payment.getReceiverAccountId()
            );
            throw e;
        }
    }
}
