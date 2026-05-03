package org.edu.infi_payment_system.Payment.exception.handler;


import org.edu.infi_payment_system.Payment.exception.custom.AccountNotFoundException;
import org.edu.infi_payment_system.Payment.exception.custom.InsufficientBalanceException;
import org.edu.infi_payment_system.Payment.exception.custom.PaymentIdNotFoundException;
import org.edu.infi_payment_system.Payment.exception.payload.ErrorCodes;
import org.edu.infi_payment_system.Payment.exception.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler{

    private ErrorResponse buildError(String errorCode ,String message, HttpStatus status) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .status(status.value())
                .createdAt(LocalDateTime.now())
                .build();
    }
    @ExceptionHandler(PaymentIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentIdNotFound(PaymentIdNotFoundException ex){
        
        return new ResponseEntity<>(
                buildError(ErrorCodes.PAYMENT_ID_NOT_FOUND ,ex.getMessage() ,HttpStatus.NOT_FOUND)
                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex){

        return new ResponseEntity<>(
                buildError(ErrorCodes.ACCOUNT_NOT_FOUND,ex.getMessage(),HttpStatus.NOT_FOUND)
                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex){

        return new ResponseEntity<>(
                buildError(ErrorCodes.INSUFFICIENT_BALANCE,ex.getMessage(),HttpStatus.PAYMENT_REQUIRED)
                ,HttpStatus.PAYMENT_REQUIRED
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex){

        return new ResponseEntity<>(
                buildError(ErrorCodes.INTERNAL_SERVER_ERROR,"Something went wrong. Please try again later.",HttpStatus.INTERNAL_SERVER_ERROR )
                ,HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
