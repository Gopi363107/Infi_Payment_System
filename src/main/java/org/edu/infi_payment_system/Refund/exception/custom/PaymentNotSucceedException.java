package org.edu.infi_payment_system.Refund.exception.custom;

public class PaymentNotSucceedException extends RuntimeException {
    public PaymentNotSucceedException(String message) {
        super(message);
    }
}
