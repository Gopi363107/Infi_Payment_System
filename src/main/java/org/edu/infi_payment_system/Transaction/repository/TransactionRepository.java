package org.edu.infi_payment_system.Transaction.repository;

import org.edu.infi_payment_system.Transaction.entity.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionLedger, UUID> {
}
