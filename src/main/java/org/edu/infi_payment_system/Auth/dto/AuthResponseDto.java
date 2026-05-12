package org.edu.infi_payment_system.Auth.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String token;
    private String tokenType;
    private Long userId;
    private String email;
    private String role;

}
