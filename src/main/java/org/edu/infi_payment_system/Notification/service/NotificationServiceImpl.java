package org.edu.infi_payment_system.Notification.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Notification.dto.NotificationResponseDto;
import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.edu.infi_payment_system.Notification.event.NotificationEvent;
import org.edu.infi_payment_system.Notification.exception.custom.NotificationIdNotFoundException;
import org.edu.infi_payment_system.Notification.exception.custom.NotificationSendException;
import org.edu.infi_payment_system.Notification.exception.custom.NotificationStatusNotFoundException;
import org.edu.infi_payment_system.Notification.exception.custom.NotificationTypeNotFoundException;
import org.edu.infi_payment_system.Notification.mapper.NotificationMapper;
import org.edu.infi_payment_system.Notification.repository.NotificationRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Retryable(
            retryFor = NotificationSendException.class,
            maxAttempts =3,
            backoff = @Backoff(delay = 5000)
    )
    @Override
    public NotificationResponseDto sendNotification(NotificationEvent event) {

        Notifications notification = NotificationMapper.toEntity(event);

        if(serviceUnavailable()) {
            throw new NotificationSendException(
                    "Email service unavailable"
            );
        }

        notification.setStatus(NotificationStatus.SENT);
        notification.setNextRetryAt(
                null
        );
        Notifications saved = notificationRepository.save(notification);

        return NotificationMapper.toResponseDto(saved);
    }

    @Recover
    public NotificationResponseDto recover(NotificationSendException ex, NotificationEvent event
    ){

        Notifications notification = NotificationMapper.toEntity(event);

        notification.setStatus(
                NotificationStatus.FAILED
        );
        notification.setRetryCount(0);
        notification.setNextRetryAt(
                LocalDateTime.now().plusMinutes(5)
        );

        Notifications saved = notificationRepository.save(notification);

        return NotificationMapper.toResponseDto(saved);
    }

    @Override
    public NotificationResponseDto getById(UUID notificationId) {
        Notifications notification = notificationRepository
                .findById(notificationId).orElseThrow(
                        ()-> new NotificationIdNotFoundException(
                        "Notification id is not found!" + notificationId
                )
        );

        return NotificationMapper.toResponseDto(notification);
    }

    @Override
    public NotificationResponseDto markAsRead(UUID notificationId) {
        Notifications notification = notificationRepository.findById(notificationId).orElseThrow(
                ()-> new NotificationIdNotFoundException("Notification id is not found!" + notificationId)
        );

        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        Notifications saved = notificationRepository.save(notification);
        return NotificationMapper.toResponseDto(saved);
    }

    @Override
    public List<NotificationResponseDto> getByUserId(UUID userId) {
        List<Notifications> notification =  notificationRepository.findByUserId(userId);

        if(notification.isEmpty()){
            throw new NotificationIdNotFoundException(
                    "No notifications found for user ID: " + userId);
        }

        return notification.stream()
                .map(NotificationMapper :: toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDto> getAllNotification() {
        List<Notifications> notification =  notificationRepository.findAll();

        return notification.stream()
                .map(NotificationMapper :: toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDto> getNotificationByStatus(NotificationStatus status) {
        List<Notifications> notification =  notificationRepository.findByStatus(status);

        if(notification.isEmpty()){
            throw new NotificationStatusNotFoundException(
                    "No notifications found for NotificationStatus: " + status);
        }

        return notification.stream()
                .map(NotificationMapper :: toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDto> getNotificationByType(NotificationType type) {

        List<Notifications> notification =  notificationRepository.findByNotificationType(type);

        if(notification.isEmpty()){
            throw new NotificationTypeNotFoundException(
                    "No notifications found for NotificationType: " + type);
        }

        return notification.stream()
                .map(NotificationMapper :: toResponseDto)
                .collect(Collectors.toList());
    }

    private boolean serviceUnavailable(){
        return Math.random() < 0.7;
    }

}