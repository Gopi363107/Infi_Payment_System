package org.edu.infi_payment_system.Refund.exception.custom;

public class RefundIdNotFoundException extends RuntimeException {
    public RefundIdNotFoundException(String message) {
        super(message);
    }
}
