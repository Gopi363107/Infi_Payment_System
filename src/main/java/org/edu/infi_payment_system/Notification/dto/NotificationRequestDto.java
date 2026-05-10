package org.edu.infi_payment_system.Notification.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.edu.infi_payment_system.Notification.enums.NotificationReferenceType;
import org.edu.infi_payment_system.Notification.enums.NotificationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDto {

    @NotNull(message = "user id is required")
    private Long userId;

    @NotNull(message = "reference id is required")
    private Long referenceId;

    @NotBlank(message = "title is required")
    private String title;

    private String message;

    @NotNull(message = "reference type is required")
    private NotificationReferenceType notificationReferenceType;

    @NotNull(message = "notification type is required")
    private NotificationType type;

}
