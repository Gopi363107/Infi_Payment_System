package org.edu.infi_payment_system.Notification.kafka;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Notification.event.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String , NotificationEvent> kafkaTemplate;

    public void publish(NotificationEvent event){
        kafkaTemplate.send(
                "notification-topic",
                event
        );
    }
}
