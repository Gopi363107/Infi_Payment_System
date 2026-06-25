package org.edu.infi_payment_system.Admin.service;

import org.edu.infi_payment_system.Admin.dto.TransactionDetailsResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionSearchResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionStatsResponse;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AdminTransactionService {

    TransactionDetailsResponse getTransaction(UUID transactionId);
    List<TransactionSearchResponse> searchTransactions(UUID senderId,
                                                       UUID receiverId,
                                                       TransactionStatus status);

    Page<TransactionSearchResponse> getAllTransactions(Pageable pageable);

    List<TransactionSearchResponse> failedTransactions();

    TransactionStatsResponse getTransactionStats();
}
