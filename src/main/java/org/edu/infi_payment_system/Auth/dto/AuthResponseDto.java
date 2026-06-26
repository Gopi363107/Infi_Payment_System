package org.edu.infi_payment_system.Auth.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponseDto {

    private String token;
    private String tokenType;
    private UUID userId;
    private String email;
    private String role;

}
