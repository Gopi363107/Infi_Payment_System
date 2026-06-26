package org.edu.infi_payment_system.Admin.repository;

import org.edu.infi_payment_system.Admin.entity.FraudCase;
import org.edu.infi_payment_system.Admin.enums.FraudStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FraudCaseRepository extends JpaRepository<FraudCase, UUID> {

    long count();

    long countByFraudStatus(FraudStatus status);

    List<FraudCase> findByFraudStatus(FraudStatus status);

    Optional<FraudCase> findByFraudId(UUID fraudId);

    List<FraudCase> findAllByOrderByCreatedAtDesc();

    @Query("""
           SELECT COALESCE(SUM(f.transaction.amount), 0)
           FROM FraudCase f
           """)
    BigDecimal getTotalFraudAmount();
}
