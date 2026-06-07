package org.edu.infi_payment_system.Refund.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequestDto {

    @NotNull(message = "Payment id is required")
    private UUID paymentId;

    @NotNull(message = "Refunds Amount is required")
    @Positive(message = "Refunds amount must be positive")
    private BigDecimal refundAmount;

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotBlank(message = "IdempotencyKey is required")
    private String idempotencyKey;
}
