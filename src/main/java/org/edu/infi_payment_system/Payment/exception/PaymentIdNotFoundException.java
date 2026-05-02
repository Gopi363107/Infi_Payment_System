package org.edu.infi_payment_system.Payment.exception;

public class PaymentIdNotFoundException extends RuntimeException {
    public PaymentIdNotFoundException(String message) {
        super(message);
    }
}
