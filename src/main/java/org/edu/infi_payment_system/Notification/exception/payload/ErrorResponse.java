package org.edu.infi_payment_system.Notification.exception.payload;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private HttpStatus status;
    private String message;
    private LocalDateTime createdAt;
}
