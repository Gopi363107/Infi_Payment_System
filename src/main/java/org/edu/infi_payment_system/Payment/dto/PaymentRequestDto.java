package org.edu.infi_payment_system.Payment.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentRequestDto {

    @NotNull(message = "sender id is required")
    private UUID senderAccountId;

    @NotNull(message = "receiver id is required")
    private UUID receiverAccountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater then zero")
    private BigDecimal amount;

    @NotNull(message = "idempotency key is required")
    private UUID idempotencyKey;
}
