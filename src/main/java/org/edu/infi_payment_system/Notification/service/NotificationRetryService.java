package org.edu.infi_payment_system.Notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationRetryService {

    private final NotificationRepository notificationRepository;
    private final NotificationSenderService notificationSenderService;

    private static final int MAX_RETRY_COUNT = 3;

    @Transactional
    public void retryNotification(UUID notificationId){

        Notifications notification = notificationRepository.findById(notificationId)
                .orElseThrow();

        if(notification.getStatus() != NotificationStatus.FAILED){
            return;
        }

        boolean sentSuccessfully = notificationSenderService.send(notification);

        if(sentSuccessfully){

            notification.setStatus(
                    NotificationStatus.SENT
            );
            notification.setRetryCount(0);
            notification.setNextRetryAt(null);

            log.info(
                    "Notification sent successfully: {}",
                    notification.getId()
            );
        }
        else {
            handleRetry(notification);
        }

    }
    public void handleRetry(Notifications notification){

        int retryCount = notification.getRetryCount() + 1;
        notification.setRetryCount(retryCount);

        if(retryCount >= MAX_RETRY_COUNT){

            notification.setStatus(NotificationStatus.EXPIRED);

            log.warn(
                    "Notification expired after max retries : {}",
                    notification.getId()
            );

            return;
        }

        notification.setStatus(NotificationStatus.FAILED);
        notification.setNextRetryAt(
                calculateNextRetryTime(retryCount)
        );
    }

    private LocalDateTime calculateNextRetryTime(int retryCount){

        return switch (retryCount){

            case 1 -> LocalDateTime.now().plusMinutes(1);
            case 2 -> LocalDateTime.now().plusMinutes(5);
            case 3 -> LocalDateTime.now().plusMinutes(15);
            default -> LocalDateTime.now().plusHours(1);
        };
    }
}
