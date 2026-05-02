package org.edu.infi_payment_system.Payment.dto;

import lombok.Data;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {

    private Long transactionId;
    private Long senderAccountId;
    private Long receiverAccountId;
    private Double amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

}
