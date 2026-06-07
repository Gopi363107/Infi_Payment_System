package org.edu.infi_payment_system.Payment.dto;

import lombok.Data;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentResponseDto {

    private UUID transactionId;
    private UUID senderAccountId;
    private UUID receiverAccountId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

}
