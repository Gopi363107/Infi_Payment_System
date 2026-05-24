package org.edu.infi_payment_system.Auth.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Auth.exception.payload.ErrorCodes;

@RequiredArgsConstructor
@Getter
public class EmailAlreadyExistsException extends RuntimeException {

    private ErrorCodes errorCodes;

    public EmailAlreadyExistsException(String message) {
        super(ErrorCodes.USER_ALREADY_EXISTS.getMessage());
        this.errorCodes = ErrorCodes.USER_ALREADY_EXISTS;
    }

}
