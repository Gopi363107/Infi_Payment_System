package org.edu.infi_payment_system.Auth.service;


import org.edu.infi_payment_system.Auth.dto.AuthResponseDto;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto login(LoginRequestDto requestDto);
    AuthResponseDto register(RegisterRequestDto request);
}
