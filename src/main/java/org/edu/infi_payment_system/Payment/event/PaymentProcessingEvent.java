package org.edu.infi_payment_system.Payment.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessingEvent {

    private UUID paymentId;

    private UUID senderId;

    private UUID receiverId;

    private BigDecimal amount;
}
