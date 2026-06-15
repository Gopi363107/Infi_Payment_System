package org.edu.infi_payment_system.Notification.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.repository.NotificationRepository;
import org.edu.infi_payment_system.Notification.service.NotificationSenderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRetryScheduler {

    private final NotificationRepository notificationRepository;
    private final NotificationSenderService senderService;

    private static final int MAX_RETRY_COUNT = 3;

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void retryFailedNotification(){

        List<Notifications> failedNotifications = notificationRepository
                .findByStatusAndNextRetryAtBefore(
                NotificationStatus.FAILED ,
                LocalDateTime.now()
        );

        for(Notifications notification : failedNotifications){

            try{

                log.info(
                        "Retrying notification :{}",
                        notification.getId()
                );
                boolean sentSuccessfully = senderService.send(notification);

                if(sentSuccessfully){
                    notification.setStatus(NotificationStatus.SENT);

                    notification.setNextRetryAt(null);
                    notificationRepository.save(notification);

                    log.info(
                            "Notification sent successfully : {}",
                            notification.getId()
                    );
                }
                else{
                    handleRetry(notification);
                }
            }
            catch(Exception ex){

                log.info(
                  "Retry failed for notification : {}",
                        notification.getId(),
                        ex
                );
                handleRetry(notification);

            }
        }
    }

    private void handleRetry(Notifications notification){

        int retryCount = notification.getRetryCount() + 1;
        notification.setRetryCount(retryCount);

        if(retryCount >= MAX_RETRY_COUNT){

            notification.setStatus(
                    NotificationStatus.EXPIRED
            );

            notificationRepository.save(notification);

            log.warn(
                "Notification expired after max retries : {}",
                    notification.getId()
            );

            return;
        }
        notification.setNextRetryAt(
                calculateNextRetryTime(retryCount)
        );
        notificationRepository.save(notification);
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
