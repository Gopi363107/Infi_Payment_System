package org.edu.infi_payment_system.Ledger.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;


@Data
public class LedgerRequestDto {

    @NotNull(message =  "senderId is required")
    private UUID senderId ;

    @NotNull(message = "receiverId is required")
    private UUID receiverId ;

    @Positive(message = "amount must be greater then zero")
    @NotNull(message = "amount must required")
    private BigDecimal amount;

    private String remarks;
}
