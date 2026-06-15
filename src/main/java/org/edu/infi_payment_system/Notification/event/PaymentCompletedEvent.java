package org.edu.infi_payment_system.Notification.event;

import lombok.*;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCompletedEvent {

    private UUID paymentId;
    private UUID referenceId;
    private UUID senderId;
    private UUID receiverId;
    private BigDecimal amount;
    private PaymentStatus status;
}
