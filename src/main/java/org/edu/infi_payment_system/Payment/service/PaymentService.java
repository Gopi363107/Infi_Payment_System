package org.edu.infi_payment_system.Payment.service;


import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto dto);
    PaymentResponseDto getPaymentById(UUID id);
    List<PaymentResponseDto> getPaymentByAccount(UUID accountId);
    List<PaymentResponseDto> getAllPayments();
    List<PaymentResponseDto> getPaymentByStatus(PaymentStatus status);
}

/*
1. Validate request
2. Fetch sender & receiver
3. Create payment record (PENDING)
4. Call transactionService.processTransaction(...)
5. Mark payment SUCCESS or FAILED
6. Return response

PAYMENT FLOW DIAGRAM

Payment API
    |
    v
PaymentService
    |
    v
PaymentProducer
    |
    v
Kafka (payment-processing-topic)
    |
    v
PaymentConsumer
    |
    v
TransactionService
    |
    +--> Account Update
    +--> Ledger Entry
    +--> Audit Logs
    +--> Transaction Record
    +--> Cache Update
    +--> NotificationProducer
                  |
                  v
            Kafka (notification-topic)
                  |
                  v
          NotificationConsumer
                  |
                  v
          NotificationService
                  |
                  +--> SENT
                  +--> FAILED
                           |
                           v
                    Retry Scheduler
                           |
                           v
                    SENT / EXPIRED
 */
