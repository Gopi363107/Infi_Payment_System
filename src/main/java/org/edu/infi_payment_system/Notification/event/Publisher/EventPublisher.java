package org.edu.infi_payment_system.Notification.event.Publisher;

import org.edu.infi_payment_system.Notification.event.PaymentCompletedEvent;

public interface EventPublisher {
    void publishPaymentCompletedEvent(PaymentCompletedEvent event);
}
