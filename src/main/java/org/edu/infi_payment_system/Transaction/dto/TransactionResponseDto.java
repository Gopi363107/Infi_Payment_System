package org.edu.infi_payment_system.Transaction.dto;

import lombok.Data;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;

import java.math.BigDecimal;

@Data
public class TransactionResponseDto {

    private Long transactionId;
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;

    private TransactionStatus status;

}
