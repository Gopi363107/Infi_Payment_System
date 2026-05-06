package org.edu.infi_payment_system.Transaction.dto;

import lombok.Data;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;

@Data
public class TransactionResponseDto {

    private Long transactionId;
    private Long senderId;
    private Long receiverId;
    private Double amount;

    private TransactionStatus status;

}
