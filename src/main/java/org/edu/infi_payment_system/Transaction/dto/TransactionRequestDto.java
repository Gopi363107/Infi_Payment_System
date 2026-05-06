package org.edu.infi_payment_system.Transaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class TransactionRequestDto {

    @NotNull(message =  "senderId is required")
    private Long senderId ;

    @NotNull(message = "receiverId is required")
    private Long receiverId ;

    @Positive(message = "amount must be greater then zero")
    @NotNull(message = "amount must required")
    private Double amount;

    private String remarks;
}
