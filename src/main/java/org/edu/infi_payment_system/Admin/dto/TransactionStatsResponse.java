package org.edu.infi_payment_system.Admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatsResponse {

    private long totalTransactions;
    private long successfulTransactions;
    private long failedTransactions;
    private BigDecimal totalAmountTransferred;
}
