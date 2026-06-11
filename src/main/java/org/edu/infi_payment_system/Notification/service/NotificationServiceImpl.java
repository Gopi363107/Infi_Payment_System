package org.edu.infi_payment_system.Notification.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Notification.dto.NotificationRequestDto;
import org.edu.infi_payment_system.Notification.dto.NotificationResponseDto;
import org.edu.infi_payment_system.Notification.entity.Notifications;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
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
    public NotificationResponseDto createNotification(NotificationRequestDto dto) {

        Notifications entity = NotificationMapper.toEntity(dto);

        if(serviceUnavailable()) {
            throw new NotificationSendException(
                    "Email service unavailable"
            );
        }

        System.out.println("Notification Sent");
        Notifications saved =  notificationRepository.save(entity);

        return NotificationMapper.toResponseDto(saved);
    }
    @Recover
    public NotificationResponseDto recover(
            NotificationSendException ex,
            NotificationRequestDto dto
    ){

        Notifications entity =
                NotificationMapper.toEntity(dto);

        entity.setStatus(
                NotificationStatus.FAILED
        );

        Notifications saved =
                notificationRepository.save(entity);

        return NotificationMapper
                .toResponseDto(saved);
    }

    @Override
    public NotificationResponseDto retryNotification(UUID notificationId) {
        return null;
    }

    @Override
    public NotificationResponseDto getById(UUID notificationId) {
        return null;
    }

    @Override
    public NotificationResponseDto markAsRead(UUID notificationId) {
        return null;
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
}
