package org.edu.infi_payment_system.Refund.exception.handler;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Refund.exception.custom.PaymentIdNotFoundException;
import org.edu.infi_payment_system.Refund.exception.custom.PaymentNotSucceedException;
import org.edu.infi_payment_system.Refund.exception.custom.RefundIdNotFoundException;
import org.edu.infi_payment_system.Refund.exception.custom.RefundLimitExceededException;
import org.edu.infi_payment_system.Refund.exception.payload.ErrorCodes;
import org.edu.infi_payment_system.Refund.exception.payload.ErrorResponse;
import org.edu.infi_payment_system.Refund.service.RefundService;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
@RequiredArgsConstructor
public class RefundGlobalExceptionHandler {


    private ErrorResponse buildError(String errorCode , HttpStatus status , String message){
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .status(status.value())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(PaymentIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentIdNotFound(PaymentIdNotFoundException ex){
        return new ResponseEntity<>(
                buildError(ErrorCodes.PAYMENT_ID_NOT_FOUND ,HttpStatus.NOT_FOUND ,  ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PaymentNotSucceedException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotSucceed(PaymentNotSucceedException ex){
        return new ResponseEntity<>(
                buildError(ErrorCodes.PAYMENT_NOT_SUCCEED , HttpStatus.BAD_REQUEST , ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RefundIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefundIdNotFound(RefundIdNotFoundException ex){
        return new ResponseEntity<>(
                buildError(ErrorCodes.REFUND_ID_NOT_FOUND , HttpStatus.NOT_FOUND , ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RefundLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRefundLimitExceeded(RefundLimitExceededException ex){
        return new ResponseEntity<>(
                buildError(ErrorCodes.REFUND_LIMIT_EXCEEDED , HttpStatus.CONFLICT , ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerDown(Exception ex){
        ex.printStackTrace();

        return new ResponseEntity<>(
                buildError(
                        ErrorCodes.INTERNAL_SERVER_ERROR ,
                        HttpStatus.INTERNAL_SERVER_ERROR ,
                        "Internal server error"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
