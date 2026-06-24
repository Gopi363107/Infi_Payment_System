package org.edu.infi_payment_system.Admin.controller;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.UserDetailsResponse;
import org.edu.infi_payment_system.Admin.dto.UserSearchResponse;
import org.edu.infi_payment_system.Admin.dto.UserTransactionResponse;
import org.edu.infi_payment_system.Admin.service.AdminUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final AdminUserService adminUserService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable  UUID userId){

        return ResponseEntity.ok(
                adminUserService.getUser(userId)
        );
    }

    @GetMapping("/serach")
    public ResponseEntity<List<UserSearchResponse>> searchUser(
            @RequestParam(required = false)String  name,
        @RequestParam(required = false) String email ,
        @RequestParam(required = false) String status){

        return ResponseEntity.ok(
                adminUserService.searchUser(name, email, status)
        );
    }

    @PutMapping("/{userId}/freeze")
    public ResponseEntity<String> freezeUser(
            @PathVariable UUID userId) {

        adminUserService.freezeUser(userId);

        return ResponseEntity.ok(
                "User frozen successfully");
    }

    @PutMapping("/{userId}/unfreeze")
    public ResponseEntity<String> unfreezeUser(
            @PathVariable UUID userId) {

        adminUserService.unFreezeUser(userId);

        return ResponseEntity.ok(
                "User unfrozen successfully");
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<List<UserTransactionResponse>>
    getUserTransactions(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                adminUserService.getUserTransaction(userId)
        );
    }

}
