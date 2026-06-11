package org.edu.infi_payment_system.Notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.edu.infi_payment_system.Notification.enums.NotificationReferenceType;
import org.edu.infi_payment_system.Notification.enums.NotificationType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDto {

    @NotNull(message = "reference id is required")
    private UUID referenceId;

    @NotNull(message = "user id is required")
    private UUID userId;

    @Size(max = 100, message = "title cannot exceed 100 characters")
    @NotBlank(message = "title is required")
    private String title;

    @Size(max = 500, message = "message cannot exceed 500 characters")
    @NotBlank(message = "message is required")
    private String message;

    @NotNull(message = "reference type is required")
    private NotificationReferenceType notificationReferenceType;

    @NotNull(message = "notification type is required")
    private NotificationType type;

}
