package org.edu.infi_payment_system.Transaction.exception.custom;

public class PaymentIdNotFoundException extends RuntimeException {
    public PaymentIdNotFoundException(String message) {
        super(message);
    }
}
