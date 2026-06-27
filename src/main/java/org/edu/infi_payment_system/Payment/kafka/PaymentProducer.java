package org.edu.infi_payment_system.Payment.kafka;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Payment.event.PaymentProcessingEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(PaymentProcessingEvent event){
        kafkaTemplate.send(
                "payment-processing-topic",
                event.getPaymentId().toString(),
                event
        );
    }
}
