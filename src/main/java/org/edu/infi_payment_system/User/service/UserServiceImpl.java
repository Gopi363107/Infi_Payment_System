package org.edu.infi_payment_system.User.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;
import org.edu.infi_payment_system.User.entity.AppUser;
import org.edu.infi_payment_system.User.exception.InvalidCredentialsException;
import org.edu.infi_payment_system.User.exception.UserAlreadyExistsException;
import org.edu.infi_payment_system.User.mapper.UserMapper;
import org.edu.infi_payment_system.User.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public UserResponseDto registerUser(RegisterRequestDto requestDto){

        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        AppUser user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        AppUser savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public String loginUser(LoginRequestDto requestDto){

        AppUser user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email"));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid password");
        }

        return "dummy token - H389Y3BNDO3289NMNNDU3820BJD62J";
    }
    @Override
    public UserResponseDto getUserById(Long id){

        AppUser user = userRepository.findById(id).orElseThrow( () -> new UsernameNotFoundException("user not found with id "+ id));

        return userMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new UsernameNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }
}
