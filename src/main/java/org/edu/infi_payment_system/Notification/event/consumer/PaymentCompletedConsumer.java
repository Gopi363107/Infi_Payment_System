package org.edu.infi_payment_system.Notification.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Notification.dto.NotificationRequestDto;
import org.edu.infi_payment_system.Notification.enums.ReferenceType;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.edu.infi_payment_system.Notification.event.PaymentCompletedEvent;
import org.edu.infi_payment_system.Notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PaymentCompletedConsumer {

    private  final NotificationService notificationService;

    @KafkaListener(
            topics = "payment-completed",
            groupId = "notification-group"
    )
    public void consume(PaymentCompletedEvent event){

        log.info(
                "Received payment completed event : {}",
                event.getPaymentId()
        );

        NotificationRequestDto request = NotificationRequestDto.builder()
                .userId(event.getReceiverId())
                .referenceId(event.getReferenceId())
                .title("Payment Received")
                .message("Amount ₹" +
                        event.getAmount() +
                        " received successfully"
                )
                .notificationReferenceType(ReferenceType.PAYMENT)
                .type(NotificationType.IN_APP)
                .build();

        notificationService.sendNotification(request);
    }
}
