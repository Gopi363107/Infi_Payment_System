package org.edu.infi_payment_system.User.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserResponseDto registerUser(@Valid RegisterRequestDto requestDto){

    }

    public String loginUser(@Valid LoginRequestDto requestDto){

    }

    public UserResponseDto getUserById(Long id){

    }

    public List<UserResponseDto> getAllUsers(){

    }

    public void deleteUser(Long id){

    }
}
