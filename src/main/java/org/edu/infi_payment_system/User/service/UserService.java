package org.edu.infi_payment_system.User.service;


import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(RegisterRequestDto requestDto);

    String loginUser(LoginRequestDto requestDto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    void deleteUser(Long id);
}
