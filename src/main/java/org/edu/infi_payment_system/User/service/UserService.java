package org.edu.infi_payment_system.User.service;


import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto registerUser(RegisterRequestDto requestDto);

    String loginUser(LoginRequestDto requestDto);

    UserResponseDto getUserById(UUID id);

    List<UserResponseDto> getAllUsers();

    void deleteUser(UUID id);
}
