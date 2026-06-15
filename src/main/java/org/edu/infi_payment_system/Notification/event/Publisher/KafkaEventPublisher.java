package org.edu.infi_payment_system.Notification.event.Publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Notification.event.PaymentCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher{

    private final KafkaTemplate<String , PaymentCompletedEvent> kafkaTemplate;
    private final String TOPIC = "payment-completed";

    @Override
    public void publishPaymentCompletedEvent(PaymentCompletedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getPaymentId().toString(),
                event
        );

        log.info(
                "Published payment completed event : {}",
                event.getPaymentId()
        );
    }
}
