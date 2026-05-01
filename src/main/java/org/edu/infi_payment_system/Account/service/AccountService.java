package org.edu.infi_payment_system.Account.service;


import org.edu.infi_payment_system.Account.dto.AccountRequestDto;
import org.edu.infi_payment_system.Account.dto.AccountResponseDto;

import java.util.List;


public interface AccountService {

    AccountResponseDto createAccount(AccountRequestDto requestDto);

    AccountResponseDto updateAccount(Long id ,AccountRequestDto requestDto);

    AccountResponseDto getAccountById(Long id);

    List<AccountResponseDto> getAllAccounts();

    void deleteAccount(Long id);
}
