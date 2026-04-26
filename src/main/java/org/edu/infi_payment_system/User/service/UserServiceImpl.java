package org.edu.infi_payment_system.User.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;
import org.edu.infi_payment_system.User.mapper.UserMapper;
import org.edu.infi_payment_system.User.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto registerUser(@Valid RegisterRequestDto requestDto){
        return new UserResponseDto();
    }

    @Override
    public String loginUser(@Valid LoginRequestDto requestDto){
        return "dummy token - H389Y3BNDO3289NMNNDU3820BJD62J";
    }
    @Override
    public UserResponseDto getUserById(Long id){
        return new UserResponseDto();
    }

    @Override
    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map(user-> new UserResponseDto()).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
