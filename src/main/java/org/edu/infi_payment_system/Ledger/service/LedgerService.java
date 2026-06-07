package org.edu.infi_payment_system.Ledger.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface LedgerService {
    void createDoubleEntryLedger(UUID transactionId,
                                      UUID senderId,
                                      UUID receiverId,
                                      BigDecimal amount);

}
