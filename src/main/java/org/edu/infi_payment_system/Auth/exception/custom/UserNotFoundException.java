package org.edu.infi_payment_system.Auth.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Auth.exception.payload.ErrorCodes;

@Getter
@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private final ErrorCodes errorCodes;

    public UserNotFoundException(String message) {
        super(ErrorCodes.USER_NOT_FOUND.getMessage());
        this.errorCodes = ErrorCodes.USER_NOT_FOUND;
    }
}
