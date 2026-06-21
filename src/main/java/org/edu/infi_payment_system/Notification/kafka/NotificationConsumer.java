package org.edu.infi_payment_system.Notification.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Notification.event.NotificationEvent;
import org.edu.infi_payment_system.Notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "notification-topic",
            groupId = "notification-group"
    )
    public void consume(NotificationEvent event) {

        log.info(
                "Received notification event for payment: {}",
                event.getPaymentId()
        );

        notificationService.sendNotification(event);
    }
}
