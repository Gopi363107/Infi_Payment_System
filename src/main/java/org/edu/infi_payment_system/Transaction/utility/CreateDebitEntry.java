package org.edu.infi_payment_system.Transaction.utility;

import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.edu.infi_payment_system.Transaction.entity.TransactionLedger;
import org.edu.infi_payment_system.Transaction.enums.TransactionEntryType;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;

public class CreateDebitEntry {
    public static TransactionLedger createDebitEntry(
            Long transactionId,
            String pairEntryId,
            Long senderId,
            Long receiverId,
            Double amount
    ) {
        TransactionLedger entry = new TransactionLedger();

        entry.setTransactionId(transactionId);
        entry.setAccountId(senderId);
        entry.setEntryType(TransactionEntryType.DEBIT);
        entry.setAmount(amount);

        entry.setPairEntryId(pairEntryId);
        entry.setFromAccountId(senderId);
        entry.setToAccountId(receiverId);

        entry.setTransactionStatus(TransactionStatus.SUCCESS);

        return entry;
    }
}
