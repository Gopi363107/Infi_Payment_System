package org.edu.infi_payment_system.Account.exception.custom;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String message){ super(message);}
}
