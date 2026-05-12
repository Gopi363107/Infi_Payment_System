package org.edu.infi_payment_system.Auth.controller;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Auth.dto.AuthResponseDto;
import org.edu.infi_payment_system.Auth.service.AuthService;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto request){
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody RegisterRequestDto request){
        return authService.register(request);
    }
}
