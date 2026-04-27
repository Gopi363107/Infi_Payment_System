package org.edu.infi_payment_system.User.exception;

public class InvalidCredentialsException  extends RuntimeException{
    public InvalidCredentialsException(String message){
        super(message);
    }
}
