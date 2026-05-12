package org.edu.infi_payment_system.Notification.exception.handler;

import org.edu.infi_payment_system.Notification.exception.custom.NotificationIdNotFoundException;
import org.edu.infi_payment_system.Notification.exception.custom.NotificationStatusNotFoundException;
import org.edu.infi_payment_system.Notification.exception.custom.NotificationTypeNotFoundException;
import org.edu.infi_payment_system.Notification.exception.payload.ErrorCode;
import org.edu.infi_payment_system.Notification.exception.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class NotificationExceptionHandler {

    public ErrorResponse buildError(String errorCode , HttpStatus status , String message){
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .status(status)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotificationIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationIdNotFound(
            NotificationIdNotFoundException ex){

        return new ResponseEntity<>(
                buildError(ErrorCode.NOTIFICATION_ID_NOT_FOUND,
                        HttpStatus.NOT_FOUND,ex.getMessage() )
                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotificationTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationTypeNotFound(
            NotificationIdNotFoundException ex){

        return new ResponseEntity<>(
                buildError(ErrorCode.NOTIFICATION_TYPE_NOT_FOUND,
                        HttpStatus.NOT_FOUND,ex.getMessage() )
                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotificationStatusNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationStatusNotFound(
            NotificationStatusNotFoundException ex){

        return new ResponseEntity<>(
                buildError(ErrorCode.NOTIFICATION_STATUS_NOT_FOUND,
                        HttpStatus.NOT_FOUND,ex.getMessage() )
                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex){

        return new ResponseEntity<>(
                buildError(ErrorCode.INTERNAL_SERVER_ERROR
                        ,HttpStatus.INTERNAL_SERVER_ERROR,
                        "Something went wrong. Please try again later." )
                ,HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
