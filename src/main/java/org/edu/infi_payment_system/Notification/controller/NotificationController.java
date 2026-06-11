package org.edu.infi_payment_system.Notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Notification.dto.NotificationRequestDto;
import org.edu.infi_payment_system.Notification.dto.NotificationResponseDto;
import org.edu.infi_payment_system.Notification.enums.NotificationStatus;
import org.edu.infi_payment_system.Notification.enums.NotificationType;
import org.edu.infi_payment_system.Notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDto> createNotification(
            @Valid @RequestBody NotificationRequestDto dto){
        return ResponseEntity.ok(notificationService.createNotification(dto));
    }

    @PostMapping("/{notificationId}/retry")
    public ResponseEntity<NotificationService> retryNotification(@PathVariable UUID notificationId){
        return ResponseEntity.ok(notificationService.retryNotification(notificationId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDto>> getByUserId(@PathVariable UUID userId){
        return ResponseEntity.ok(notificationService.getByUserId(userId));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDto> getById(@PathVariable UUID notificationId){
        return ResponseEntity.ok(
                notificationService.getById(notificationId)
        );
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponseDto> markAsRead(@PathVariable UUID notificationId){

        return ResponseEntity.ok(
                notificationService.markAsRead(notificationId)
        );
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getAllNotification(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size){

        return ResponseEntity.ok(notificationService.getAllNotification());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponseDto>> getNotificationByStatus(
            @PathVariable NotificationStatus status){
        return ResponseEntity.ok(notificationService.getNotificationByStatus(status));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationResponseDto>> getNotificationByType(
            @PathVariable NotificationType type){
        return ResponseEntity.ok(notificationService.getNotificationByType(type));
    }
}
