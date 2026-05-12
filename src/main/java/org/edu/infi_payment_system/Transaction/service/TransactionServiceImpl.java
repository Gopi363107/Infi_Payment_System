package org.edu.infi_payment_system.Transaction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;
import org.edu.infi_payment_system.Transaction.entity.TransactionLedger;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.edu.infi_payment_system.Transaction.repository.TransactionRepository;
import org.edu.infi_payment_system.Transaction.utility.CreateCreditEntry;
import org.edu.infi_payment_system.Transaction.utility.CreateDebitEntry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionLedgerService{

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransactionResponseDto createDoubleEntryTransaction(
            Long transactionId,
            Long senderId,
            Long receiverId,
            BigDecimal amount
    ) {

        String pairEntryId = java.util.UUID.randomUUID().toString();

        TransactionLedger debitEntry = CreateDebitEntry.createDebitEntry(
                transactionId ,
                pairEntryId ,
                senderId,
                receiverId,
                amount
        );

        TransactionLedger creditEntry = CreateCreditEntry.createCreditEntry(
                transactionId ,
                pairEntryId ,
                senderId,
                receiverId,
                amount
        );

        transactionRepository.saveAll(List.of(debitEntry, creditEntry));

        TransactionResponseDto response = new TransactionResponseDto();
        response.setTransactionId(transactionId);
        response.setSenderId(senderId);
        response.setReceiverId(receiverId);
        response.setAmount(amount);
        response.setStatus(TransactionStatus.SUCCESS);

        return response;
    }

}
