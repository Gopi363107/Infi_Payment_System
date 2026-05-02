package org.edu.infi_payment_system.Payment.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentRequestDto {

    @NotNull(message = "sender id is required")
    private Long senderAccountId;

    @NotNull(message = "receiver id is required")
    private Long receiverAccountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater then zero")
    private Double amount;

}
