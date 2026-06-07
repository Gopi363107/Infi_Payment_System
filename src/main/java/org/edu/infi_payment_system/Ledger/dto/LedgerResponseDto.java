package org.edu.infi_payment_system.Ledger.dto;

import lombok.Data;
import org.edu.infi_payment_system.Ledger.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LedgerResponseDto {

    private UUID transactionId;
    private UUID senderId;
    private UUID receiverId;
    private BigDecimal amount;

    private TransactionStatus status;

}
