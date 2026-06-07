package org.edu.infi_payment_system.Transaction.exception.handler;

import org.edu.infi_payment_system.Transaction.exception.payload.ErrorCodes;
import org.edu.infi_payment_system.Transaction.exception.payload.ErrorResponse;
import org.edu.infi_payment_system.Transaction.exception.custom.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TransactionExceptionHandler {

    private ErrorResponse buildError(String errorCode ,String message, HttpStatus status) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .status(status.value())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex){

        return new ResponseEntity<>(
                buildError(ErrorCodes.INSUFFICIENT_BALANCE,ex.getMessage(), HttpStatus.PAYMENT_REQUIRED)
                ,HttpStatus.PAYMENT_REQUIRED
        );
    }
}
