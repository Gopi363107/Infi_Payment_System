package org.edu.infi_payment_system.Admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.User.enums.AccountStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {

    private UUID userId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean verified;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
}
