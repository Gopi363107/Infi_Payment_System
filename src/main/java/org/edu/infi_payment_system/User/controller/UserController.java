package org.edu.infi_payment_system.User.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.User.dto.request.LoginRequestDto;
import org.edu.infi_payment_system.User.dto.request.RegisterRequestDto;
import org.edu.infi_payment_system.User.dto.response.UserResponseDto;
import org.edu.infi_payment_system.User.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 1. register user
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto requestDto){
        UserResponseDto response = userService.registerUser(requestDto);
        return ResponseEntity.ok(response);
    }

    // 2. Login User (JWT later)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @Valid @RequestBody LoginRequestDto requestDto) {

        String token = userService.loginUser(requestDto);
        return ResponseEntity.ok(token);
    }

    //3. Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(
    @PathVariable Long id) {
          UserResponseDto user = userService.getUserById(id);
          return ResponseEntity.ok(user);
    }

    // 4. Get All Users (Admin)
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {

        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 5. Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
