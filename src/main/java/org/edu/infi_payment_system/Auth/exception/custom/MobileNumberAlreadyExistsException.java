package org.edu.infi_payment_system.Auth.exception.custom;

public class MobileNumberAlreadyExistsException extends RuntimeException {
    public MobileNumberAlreadyExistsException(String message) {
        super(message);
    }
}
