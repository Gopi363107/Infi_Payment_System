package org.edu.infi_payment_system.Transaction.service;

import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionLedgerService {
    TransactionResponseDto createDoubleEntryTransaction(UUID transactionId,
                                                        Long senderId,
                                                        Long receiverId,
                                                        BigDecimal amount);
}
