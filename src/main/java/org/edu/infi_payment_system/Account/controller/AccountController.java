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
import java.util.UUID;

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

    @PatchMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable UUID accountId , @Valid @RequestBody AccountRequestDto dto){
        AccountResponseDto updatedAccount = accountService.updateAccount(accountId , dto);
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable UUID accountId){
        AccountResponseDto responseId = accountService.getAccountById(accountId);
        return ResponseEntity.ok(responseId);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(){
        List<AccountResponseDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId){
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
