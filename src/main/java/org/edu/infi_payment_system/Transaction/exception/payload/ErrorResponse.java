package org.edu.infi_payment_system.Transaction.exception.payload;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ErrorResponse {

    private String errorCode;
    private String message;
    private int status;
    private LocalDateTime createdAt;
}
