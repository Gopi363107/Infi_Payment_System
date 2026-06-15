package org.edu.infi_payment_system.Notification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.edu.infi_payment_system.Notification.enums.ReferenceType;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications" , indexes = {
        @Index(name = "to_idx_notification_userId" , columnList = "userId"),
        @Index(name = "idx_notification_status", columnList = "status"),
        @Index(name="idx_notification_retry" , columnList = "status,nextRetryAt")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID referenceId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReferenceType ReferenceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    @Builder.Default
    private int retryCount = 0;

    private LocalDateTime nextRetryAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime readAt;
}
