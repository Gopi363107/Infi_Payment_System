package org.edu.infi_payment_system.Notification.exception.payload;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private String errorCode;
    private HttpStatus status;
    private String message;
    private LocalDateTime createdAt;
}
