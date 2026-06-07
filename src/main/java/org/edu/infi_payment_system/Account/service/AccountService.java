package org.edu.infi_payment_system.Account.service;


import org.edu.infi_payment_system.Account.dto.AccountRequestDto;
import org.edu.infi_payment_system.Account.dto.AccountResponseDto;

import java.util.List;
import java.util.UUID;


public interface AccountService {

    AccountResponseDto createAccount(AccountRequestDto requestDto);

    AccountResponseDto updateAccount(UUID id , AccountRequestDto requestDto);

    AccountResponseDto getAccountById(UUID id);

    List<AccountResponseDto> getAllAccounts();

    void deleteAccount(UUID id);
}
