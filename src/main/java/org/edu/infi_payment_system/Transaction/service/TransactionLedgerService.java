package org.edu.infi_payment_system.Transaction.service;

import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;

import java.math.BigDecimal;

public interface TransactionLedgerService {
    TransactionResponseDto createDoubleEntryTransaction(Long transactionId,
                                                        Long senderId,
                                                        Long receiverId,
                                                        BigDecimal amount);
}
