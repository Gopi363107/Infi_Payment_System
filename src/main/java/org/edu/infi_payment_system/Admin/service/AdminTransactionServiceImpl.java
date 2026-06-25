package org.edu.infi_payment_system.Admin.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.TransactionDetailsResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionSearchResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionStatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminTransactionServiceImpl implements AdminTransactionService{

    @Override
    public TransactionDetailsResponse getTransaction(UUID transactionId) {
        return null;
    }

    @Override
    public List<TransactionSearchResponse> searchTransactions(UUID senderId, UUID receiverId, String status) {
        return List.of();
    }

    @Override
    public List<TransactionSearchResponse> failedTransactions() {
        return List.of();
    }

    @Override
    public TransactionStatsResponse getTransactionStats() {
        return null;
    }

    @Override
    public Page<TransactionSearchResponse> getAllTransactions(Pageable pageable) {
        return null;
    }
}
