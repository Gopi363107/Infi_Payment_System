package org.edu.infi_payment_system.Account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.edu.infi_payment_system.Account.enums.AccountType;

@Getter
@Setter
public class AccountRequestDto {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotBlank(message = "bankName is required")
    private String bankName;

    @NotBlank(message = "accountNumber is required")
    @Size(min = 9, max = 18)
    private String accountNumber;

    @NotBlank(message = "ifscCode is required")
    @Size(min = 11, max = 11)
    private String ifscCode;

    @NotBlank(message = "accountHolderName is required")
    private String accountHolderName;

    private AccountType accountType = AccountType.SAVINGS;
}
