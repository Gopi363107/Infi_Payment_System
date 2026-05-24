package org.edu.infi_payment_system.Auth.exception.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes {

    USER_NOT_FOUND("AUTH_001","user not found"),
    INVALID_CREDENTIALS("AUTH_002" ,"Invalid email or password"),
    USER_ALREADY_EXISTS("AUTH_003" , "email is already exists"),
    TOKEN_EXPIRED("AUTH_004" , "Token is already expired"),
    ACCESS_DENIED("AUTH_005" ,"Access denied "),
    INTERNAL_SERVER_ERROR("AUTH_500" , "Something went wrong");

    private final String code;
    private final String message;
}
