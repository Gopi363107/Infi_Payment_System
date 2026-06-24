package org.edu.infi_payment_system.Admin.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.User.enums.AccountStatus;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchResponse {

    private UUID userId;
    private String name;
    private String email;
    private AccountStatus accountStatus;
}
