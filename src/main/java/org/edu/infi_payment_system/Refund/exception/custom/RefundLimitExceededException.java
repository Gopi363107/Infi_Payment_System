package org.edu.infi_payment_system.Refund.exception.custom;

public class RefundLimitExceededException extends RuntimeException {
    public RefundLimitExceededException(String message) {
        super(message);
    }
}
