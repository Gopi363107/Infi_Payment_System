package org.edu.infi_payment_system.Transaction.exception.custom;

public class TransactionIdNotFoundException extends RuntimeException {
    public TransactionIdNotFoundException(String message) {
        super(message);
    }
}
