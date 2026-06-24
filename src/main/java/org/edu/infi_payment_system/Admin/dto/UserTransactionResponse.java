package org.edu.infi_payment_system.Admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionResponse {

    private UUID paymentId;
    private UUID transactionId;
    private UUID senderId;
    private UUID receiverId;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
}
