package org.edu.infi_payment_system.Notification.repository;

import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {

    List<Notifications> findByStatus(NotificationStatus status);

    List<Notifications> findByNotificationType(NotificationType type);

    List<Notifications> findByUserId(Long userId);
}
