package org.edu.infi_payment_system.Auth.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Auth.dto.AuthResponseDto;
import org.edu.infi_payment_system.Auth.exception.custom.EmailAlreadyExistsException;
import org.edu.infi_payment_system.Auth.exception.custom.MobileNumberAlreadyExistsException;
import org.edu.infi_payment_system.Auth.exception.custom.UserNotFoundException;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.entity.Users;
import org.edu.infi_payment_system.User.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponseDto register(RegisterRequestDto request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already registered");
        }

        if(userRepository.existsByMobileNumber(request.getMobileNumber())){
            throw new MobileNumberAlreadyExistsException("Mobile number already registered");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Users savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        AuthResponseDto response = new AuthResponseDto();
        response.setToken(token);
        response.setTokenType("Bearer");
        response.setUserId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().name());

        return response;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto requestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );

        Users user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Prepare response
        AuthResponseDto response = new AuthResponseDto();
        response.setToken(token);
        response.setTokenType("Bearer");
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        return response;
    }


}
