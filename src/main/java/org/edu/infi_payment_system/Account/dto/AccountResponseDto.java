package org.edu.infi_payment_system.Account.dto;

import lombok.Getter;
import lombok.Setter;
import org.edu.infi_payment_system.Account.enums.AccountStatus;
import org.edu.infi_payment_system.Account.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountResponseDto {

    private Long accountId;
    private String bankName;
    private String accountHolderName;
    private AccountType accountType;
    private String maskedAccountNumber;
    private Double balance;
    private AccountStatus status;
    private LocalDateTime createdAt;
}
