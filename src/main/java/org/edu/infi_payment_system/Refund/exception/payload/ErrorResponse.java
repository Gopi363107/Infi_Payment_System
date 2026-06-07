package org.edu.infi_payment_system.Refund.exception.payload;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String errorCode;
    private int status;
    private String message;
    private LocalDateTime createdAt;

}
