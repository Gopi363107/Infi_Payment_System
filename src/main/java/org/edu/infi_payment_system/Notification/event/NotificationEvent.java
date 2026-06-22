package org.edu.infi_payment_system.Notification.event;

import lombok.*;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.edu.infi_payment_system.Notification.enums.ReferenceType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEvent {

    private UUID paymentId;
    private UUID userId;
    private String title;
    private String message;
    private NotificationType notificationType;
    private ReferenceType referenceType;
}
