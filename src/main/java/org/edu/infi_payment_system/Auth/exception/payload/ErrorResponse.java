package org.edu.infi_payment_system.Auth.exception.payload;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String errorCode;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
}
