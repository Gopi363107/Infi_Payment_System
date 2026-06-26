package org.edu.infi_payment_system.Admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudStatsResponse {

    // Overall Fraud Statistics
    private Long totalFraudCases;
    private Long pendingCases;
    private Long investigatingCases;
    private Long confirmedFraudCases;
    private Long rejectedCases;

    // Financial Statistics
    private BigDecimal totalFraudAmount;
    private BigDecimal preventedLossAmount;
    private BigDecimal recoveredAmount;

    // Risk Statistics
    private Double averageRiskScore;
    private Integer highestRiskScore;
}