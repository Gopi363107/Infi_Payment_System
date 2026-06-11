package org.edu.infi_payment_system.Notification.service;

import org.edu.infi_payment_system.Notification.dto.NotificationRequestDto;
import org.edu.infi_payment_system.Notification.dto.NotificationResponseDto;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    NotificationResponseDto createNotification(NotificationRequestDto dto);
    List<NotificationResponseDto> getByUserId(UUID userId);
    List<NotificationResponseDto> getAllNotification();
    List<NotificationResponseDto> getNotificationByStatus(NotificationStatus status);
    List<NotificationResponseDto> getNotificationByType(NotificationType type);
    NotificationResponseDto markAsRead(UUID notificationId);
    NotificationResponseDto getById(UUID notificationId);
    NotificationResponseDto retryNotification(UUID notificationId);
}
