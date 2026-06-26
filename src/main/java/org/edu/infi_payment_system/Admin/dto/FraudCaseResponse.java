package org.edu.infi_payment_system.Admin.dto;

import org.edu.infi_payment_system.Admin.enums.FraudStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudCaseResponse {

    private UUID fraudCaseId;
    private UUID transactionId;
    private UUID userId;

    private String fraudReason;
    private Integer riskScore;
    private FraudStatus status;

    private UUID reviewedBy;
    private String remarks;

    private LocalDateTime detectedAt;
    private LocalDateTime reviewedAt;
}
