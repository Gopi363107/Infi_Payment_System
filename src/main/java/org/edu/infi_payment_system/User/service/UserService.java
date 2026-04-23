package org.edu.infi_payment_system.User.service;

import jakarta.validation.Valid;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(@Valid RegisterRequestDto requestDto);

    String loginUser(@Valid LoginRequestDto requestDto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    void deleteUser(Long id);
}
