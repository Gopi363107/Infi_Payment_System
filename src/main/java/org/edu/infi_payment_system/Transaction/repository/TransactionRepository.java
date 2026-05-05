package org.edu.infi_payment_system.Transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository implements JpaRepository<TransactionLedger ,Long> {
}
