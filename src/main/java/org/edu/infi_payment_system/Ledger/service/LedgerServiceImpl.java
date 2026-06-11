package org.edu.infi_payment_system.Ledger.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Ledger.dto.LedgerResponseDto;
import org.edu.infi_payment_system.Ledger.entity.Ledgers;
import org.edu.infi_payment_system.Ledger.enums.TransactionStatus;
import org.edu.infi_payment_system.Ledger.repository.LedgerRepository;
import org.edu.infi_payment_system.Ledger.utility.CreateCreditEntry;
import org.edu.infi_payment_system.Ledger.utility.CreateDebitEntry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository transactionRepository;

    @Override
    @Transactional
    public void createDoubleEntryLedger(
            UUID transactionId,
            UUID senderId,
            UUID receiverId,
            BigDecimal amount
    ) {

        log.info("Ledger pairEntryId is created!");
        String pairEntryId = java.util.UUID.randomUUID().toString();

        log.info("debitEntry is created!");
        Ledgers debitEntry = CreateDebitEntry.createDebitEntry(
                transactionId ,
                pairEntryId ,
                senderId,
                receiverId,
                amount
        );

        log.info("creditEntry is created!");
        Ledgers creditEntry = CreateCreditEntry.createCreditEntry(
                transactionId ,
                pairEntryId ,
                senderId,
                receiverId,
                amount
        );

        transactionRepository.saveAll(List.of(debitEntry, creditEntry));

        log.info("ledger entry's are completed!");

        LedgerResponseDto response = new LedgerResponseDto();
        response.setTransactionId(transactionId);
        response.setSenderId(senderId);
        response.setReceiverId(receiverId);
        response.setAmount(amount);
        response.setStatus(TransactionStatus.SUCCESS);

    }

}
