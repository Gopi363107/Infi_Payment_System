package org.edu.infi_payment_system.Auth.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.edu.infi_payment_system.Auth.exception.custom.EmailAlreadyExistsException;
import org.edu.infi_payment_system.Auth.exception.custom.MobileNumberAlreadyExistsException;
import org.edu.infi_payment_system.Auth.exception.custom.UserNotFoundException;
import org.edu.infi_payment_system.Auth.exception.payload.ErrorCodes;
import org.edu.infi_payment_system.Auth.exception.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse
    buildError(ErrorCodes errorCodes, String message , HttpStatus status, HttpServletRequest request) {
        return ErrorResponse.builder()
                .errorCode(errorCodes.getCode())
                .message(message)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(MobileNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
    handleMobileNumberAlreadyExists(MobileNumberAlreadyExistsException ex ,
                                    HttpServletRequest request){

        return new ResponseEntity<>(
                buildError(
                        ErrorCodes.USER_ALREADY_EXISTS ,
                        ex.getMessage() ,
                        HttpStatus.CONFLICT,
                        request
                )
                ,HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleUserNotFound(UserNotFoundException ex , HttpServletRequest request){

        return new ResponseEntity<>(
                buildError(
                        ErrorCodes.USER_NOT_FOUND ,
                        ex.getMessage() ,
                        HttpStatus.NOT_FOUND,
                        request
                )

                ,HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
    handleAlreadyExistsEmail(EmailAlreadyExistsException ex , HttpServletRequest request){

        return new ResponseEntity<>(
                buildError(
                        ErrorCodes.USER_ALREADY_EXISTS ,
                        ex.getMessage() ,
                        HttpStatus.CONFLICT,
                        request
                )
                ,HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneralException(Exception ex,HttpServletRequest request){

        return new ResponseEntity<>(
                buildError(
                        ErrorCodes.INTERNAL_SERVER_ERROR,
                        "Something went wrong. Please try again later.",
                        HttpStatus.INTERNAL_SERVER_ERROR ,
                        request
                )
                ,HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
