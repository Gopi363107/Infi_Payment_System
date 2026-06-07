package org.edu.infi_payment_system.Ledger.utility;


import org.edu.infi_payment_system.Ledger.entity.Ledgers;
import org.edu.infi_payment_system.Ledger.enums.TransactionEntryType;
import org.edu.infi_payment_system.Ledger.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateCreditEntry {

    public static Ledgers createCreditEntry(
            UUID transactionId,
            String pairEntryId,
            UUID senderId,
            UUID receiverId,
            BigDecimal amount
    ) {
        Ledgers entry = new Ledgers();

        entry.setTransactionId(transactionId);
        entry.setAccountId(senderId);
        entry.setEntryType(TransactionEntryType.CREDIT);
        entry.setAmount(amount);

        entry.setPairEntryId(pairEntryId);
        entry.setFromAccountId(senderId);
        entry.setToAccountId(receiverId);

        entry.setTransactionStatus(TransactionStatus.SUCCESS);

        return entry;
    }
}
