package org.edu.infi_payment_system.Account.controller;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Account.dto.AccountRequestDto;
import org.edu.infi_payment_system.Account.dto.AccountResponseDto;
import org.edu.infi_payment_system.Account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto dto){
        AccountResponseDto responseDto = accountService.createAccount(dto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable Long id ,@Valid @RequestBody AccountRequestDto dto){
        AccountResponseDto updatedAccount = accountService.updateAccount(id , dto);
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id){
        AccountResponseDto responseId = accountService.getAccountById(id);
        return ResponseEntity.ok(responseId);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(){
        List<AccountResponseDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
