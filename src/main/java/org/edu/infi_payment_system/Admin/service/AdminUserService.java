package org.edu.infi_payment_system.Admin.service;

import org.edu.infi_payment_system.Admin.dto.UserDetailsResponse;
import org.edu.infi_payment_system.Admin.dto.UserSearchResponse;
import org.edu.infi_payment_system.Admin.dto.UserTransactionResponse;
import org.edu.infi_payment_system.User.enums.AccountStatus;

import java.util.List;
import java.util.UUID;

public interface AdminUserService {

    UserDetailsResponse getUser(UUID userId);

    List<UserSearchResponse> searchUser(String name , String email , AccountStatus status);

    void freezeUser(UUID userId);

    void unFreezeUser(UUID userId);

    List<UserTransactionResponse> getUserTransaction(UUID userId);
}
