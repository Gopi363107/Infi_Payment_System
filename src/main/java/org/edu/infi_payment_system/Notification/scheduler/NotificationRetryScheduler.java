package org.edu.infi_payment_system.Notification.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRetryScheduler {

    private final NotificationRepository notificationRepository;

    private static final int MAX_RETRY_COUNT = 3;

    @Scheduled(fixedDelay = 6000)
    public void retryFailedNotification(){

        List<Notifications> failedNotifications = notificationRepository.findByStatusAndNextRetryAtBefore(
                NotificationStatus.FAILED ,
                LocalDateTime.now()
        );

        for(Notifications notification : failedNotifications){

            try{
                boolean sentSuccessfully = sendNotification(notification);
            }
        }
    }
}
