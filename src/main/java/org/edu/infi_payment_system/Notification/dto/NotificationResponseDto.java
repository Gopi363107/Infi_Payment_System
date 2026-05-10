package org.edu.infi_payment_system.Notification.dto;

import lombok.*;
import org.edu.infi_payment_system.Notification.enums.NotificationReferenceType;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {

    private Long notificationId;
    private Long userId;
    private Long referenceId;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationReferenceType referenceType;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}
