package org.edu.infi_payment_system.Payment.exception.custom;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
