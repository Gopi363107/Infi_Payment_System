package org.edu.infi_payment_system.Notification.service;

import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.springframework.stereotype.Service;

@Service
public class NotificationSenderService {

    public boolean send(Notifications notification){
        return Math.random() > 0.5;
    }
}
