package org.edu.infi_payment_system.Admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSearchResponse {

    private UUID transactionId;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
}
