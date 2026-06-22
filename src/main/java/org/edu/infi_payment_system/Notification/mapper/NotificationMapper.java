package org.edu.infi_payment_system.Notification.mapper;

import org.edu.infi_payment_system.Notification.dto.NotificationRequestDto;
import org.edu.infi_payment_system.Notification.dto.NotificationResponseDto;
import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.event.NotificationEvent;

import java.time.LocalDateTime;

public class NotificationMapper {

    public static Notifications toEntity(NotificationRequestDto dto){
        if(dto == null)return  null;

        Notifications notification = new Notifications();
        notification.setUserId(dto.getUserId());
        notification.setReferenceId(dto.getReferenceId());
        notification.setReferenceType(dto.getNotificationReferenceType());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setNotificationType(dto.getType());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());

        return notification;
    }

    public static Notifications toEntity(NotificationEvent event){

        if(event == null) return null;

        Notifications notification = new Notifications();

        notification.setTitle(event.getTitle());
        notification.setReferenceId(event.getPaymentId());
        notification.setReferenceType(event.getReferenceType());
        notification.setNotificationType(event.getNotificationType());

        notification.setUserId(event.getUserId());
        notification.setMessage(event.getMessage());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());

        return notification;
    }

    public static NotificationResponseDto toResponseDto(Notifications notification){
        if(notification == null)return null;

        NotificationResponseDto response = new NotificationResponseDto();

        response.setUserId(notification.getUserId());
        response.setNotificationId(notification.getId());
        response.setReferenceId(notification.getReferenceId());
        response.setReferenceType(notification.getReferenceType());
        response.setType(notification.getNotificationType());
        response.setStatus(notification.getStatus());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setCreatedAt(notification.getCreatedAt());
        response.setReadAt(notification.getReadAt());

        return response;
    }
}
