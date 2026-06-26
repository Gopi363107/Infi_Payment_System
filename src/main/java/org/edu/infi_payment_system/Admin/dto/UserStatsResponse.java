package org.edu.infi_payment_system.Admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsResponse {

    private long totalUsers;
    private long activeUsers;
    private long suspendedUsers;
    private long newUsersToday;

}
