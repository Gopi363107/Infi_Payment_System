package org.edu.infi_payment_system.Notification.repository;

import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notifications, UUID> {

    List<Notifications> findByStatus(NotificationStatus status);

    List<Notifications> findByNotificationType(NotificationType type);

    Optional<Notifications> findById(UUID id);

    List<Notifications> findByStatusAndNextRetryAtBefore(NotificationStatus status ,
                                                         LocalDateTime time);

    List<Notifications> findByUserId(UUID userId);
}
