package org.edu.infi_payment_system.Payment.exception.custom;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
