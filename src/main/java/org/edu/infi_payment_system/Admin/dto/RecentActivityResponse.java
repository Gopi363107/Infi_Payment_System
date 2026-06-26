package org.edu.infi_payment_system.Admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentActivityResponse {

    private UUID auditId;
    private String adminEmail;
    private String action;
    private String description;
    private String targetUserId;
    private LocalDateTime createdAt;

}
