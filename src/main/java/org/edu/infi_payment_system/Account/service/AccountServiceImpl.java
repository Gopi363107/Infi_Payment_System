package org.edu.infi_payment_system.Account.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Account.dto.AccountRequestDto;
import org.edu.infi_payment_system.Account.dto.AccountResponseDto;
import org.edu.infi_payment_system.Account.entity.Accounts;
import org.edu.infi_payment_system.Account.exception.custom.AccountAlreadyExistsException;
import org.edu.infi_payment_system.Account.exception.custom.AccountNotFoundException;
import org.edu.infi_payment_system.Account.mapper.AccountMapper;
import org.edu.infi_payment_system.Account.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final  AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountResponseDto createAccount(AccountRequestDto dto){

        if (accountRepository.existsByUserId(dto.getUserId())) {
            throw new AccountAlreadyExistsException("Account already exists");
        }

        Accounts bankAccount = accountMapper.toEntity(dto);
        bankAccount.setAccountNumberEncrypted(passwordEncoder.encode(dto.getAccountNumber()));

        Accounts savedAccount = accountRepository.save(bankAccount);
        return accountMapper.toResponseDto(savedAccount);

    }

    @Override
    public AccountResponseDto updateAccount(UUID id , AccountRequestDto dto){
        Accounts existingAccount = accountRepository.findById(id)
                .orElseThrow(() ->  new AccountNotFoundException("Account not found"));

        if(dto.getAccountHolderName() != null){
            existingAccount.setAccountHolderName(dto.getAccountHolderName());
        }
        if(dto.getBankName() != null){
            existingAccount.setBankName(dto.getBankName());
        }

        if(dto.getAccountNumber() != null){
            existingAccount.setAccountNumberEncrypted(passwordEncoder.encode(dto.getAccountNumber()));
        }

        Accounts updatedAccount = accountRepository.save(existingAccount);
        return accountMapper.toResponseDto(updatedAccount);
    }

    @Override
    public AccountResponseDto getAccountById(UUID id){
        Accounts bankAccount = accountRepository.findById(id)
                .orElseThrow(() ->  new AccountNotFoundException("Account not found"));

        return accountMapper.toResponseDto(bankAccount);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts(){
        return accountRepository.findAll().stream()
                .map(accountMapper :: toResponseDto)
                .toList();
    }

    @Override
    public void deleteAccount(UUID id){

        Accounts account = accountRepository.findById(id)
                        .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        accountRepository.delete(account);
    }


}
