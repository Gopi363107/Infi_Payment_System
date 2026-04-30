package org.edu.infi_payment_system.Account.mapper;

import org.edu.infi_payment_system.Account.dto.AccountRequestDto;
import org.edu.infi_payment_system.Account.dto.AccountResponseDto;
import org.edu.infi_payment_system.Account.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public BankAccount toEntity(AccountRequestDto dto){
        if(dto == null)return null;

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankName(dto.getBankName());
        bankAccount.setAccountHolderName((dto.getAccountHolderName()));
        bankAccount.setIfscCode(dto.getIfscCode());
        bankAccount.setAccountType(dto.getAccountType());

        return bankAccount;
    }

    public AccountResponseDto toResponseDto(BankAccount account){
        if(account == null)return null;

        AccountResponseDto dto = new AccountResponseDto();
        dto.setAccountId(account.getId());
        dto.setAccountType(account.getAccountType());
        dto.setBankName(account.getBankName());
        dto.setMaskedAccountNumber("XXXX XXXX " + account.getLast4Digits());
        dto.setAccountHolderName(account.getAccountHolderName());
        dto.setBalance(account.getBalance());
        dto.setStatus(account.getStatus());
        dto.setCreatedAt(account.getCreatedAt());

        return dto;
    }
}
