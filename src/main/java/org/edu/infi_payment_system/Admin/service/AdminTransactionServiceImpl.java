package org.edu.infi_payment_system.Admin.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.TransactionDetailsResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionSearchResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionStatsResponse;
import org.edu.infi_payment_system.Transaction.entity.Transactions;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.edu.infi_payment_system.Transaction.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminTransactionServiceImpl implements AdminTransactionService {

    private final TransactionRepository transactionRepository;


    @Override
    public TransactionDetailsResponse getTransaction(UUID transactionId) {

        Transactions transaction = transactionRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found"));

        return mapToTransactionDetailsResponse(transaction);
    }

    @Override
    public List<TransactionSearchResponse> searchTransactions(
            UUID senderId,
            UUID receiverId,
            TransactionStatus transactionStatus) {

        List<Transactions> transactions ;

        if(senderId != null){
            transactions = transactionRepository.findBySenderId(senderId);
        }
        else if(receiverId != null){
            transactions = transactionRepository.findByReceiverId(receiverId);
        }
        else if(transactionStatus != null){
            transactions = transactionRepository.findByTransactionStatus(transactionStatus);
        }
        else{
            transactions = transactionRepository.findAll();
        }

        return transactions.stream()
                .map(this::mapToTransactionSearchResponse)
                .toList();
    }

    @Override
    public List<TransactionSearchResponse> failedTransactions() {

        List<Transactions> transactions =
                transactionRepository
                        .findByTransactionStatus(
                                TransactionStatus.FAILED
                        );

        return transactions.stream()
                .map(this::mapToTransactionSearchResponse)
                .toList();
    }

    @Override
    public TransactionStatsResponse getTransactionStats() {

        long totalTransactions =
                transactionRepository.count();

        long successfulTransactions =
                transactionRepository
                        .countByTransactionStatus(
                                TransactionStatus.SUCCESS
                        );

        long failedTransactions =
                transactionRepository
                        .countByTransactionStatus(
                                TransactionStatus.FAILED
                        );

        long pendingTransactions =
                transactionRepository.countByTransactionStatus(
                        TransactionStatus.PENDING
                );

        BigDecimal totalAmountTransferred =
                transactionRepository.getTotalAmountTransferred();


        TransactionStatsResponse response =
                new TransactionStatsResponse();

        response.setTotalTransactions(totalTransactions);
        response.setSuccessfulTransactions(successfulTransactions);
        response.setFailedTransactions(failedTransactions);
        response.setTotalAmountTransferred(totalAmountTransferred);
        response.setPendingTransactions(pendingTransactions);

        return response;
    }

    @Override
    public Page<TransactionSearchResponse> getAllTransactions(
            Pageable pageable) {

        return transactionRepository
                .findAll(pageable)
                .map(this::mapToTransactionSearchResponse);
    }

    private TransactionDetailsResponse mapToTransactionDetailsResponse(
            Transactions transaction) {

        TransactionDetailsResponse response =
                new TransactionDetailsResponse();

        response.setTransactionId(
                transaction.getTransactionId());

        response.setPaymentId(
                transaction.getPaymentId());

        response.setSenderId(
                transaction.getSenderId());

        response.setReceiverId(
                transaction.getReceiverId());

        response.setAmount(
                transaction.getAmount());

        response.setTransactionStatus(
                transaction.getTransactionStatus());

        response.setCreatedAt(
                transaction.getCreatedAt());

        return response;
    }

    private TransactionSearchResponse mapToTransactionSearchResponse(
            Transactions transaction) {

        TransactionSearchResponse response =
                new TransactionSearchResponse();

        response.setTransactionId(
                transaction.getTransactionId());

        response.setTransactionStatus(
                transaction.getTransactionStatus());

        response.setAmount(
                transaction.getAmount());

        response.setCreatedAt(
                transaction.getCreatedAt());

        return response;
    }
}