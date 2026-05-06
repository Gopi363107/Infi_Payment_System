package org.edu.infi_payment_system.Transaction.service;

import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;

public interface TransactionLedgerService {
    TransactionResponseDto createDoubleEntryTransaction(Long transactionId,
                                                        Long senderId,
                                                        Long receiverId,
                                                        Double amount
    ) ;
}
