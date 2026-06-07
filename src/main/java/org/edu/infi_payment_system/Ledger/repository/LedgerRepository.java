package org.edu.infi_payment_system.Ledger.repository;

import org.edu.infi_payment_system.Ledger.entity.Ledgers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LedgerRepository extends JpaRepository<Ledgers, UUID> {
}
