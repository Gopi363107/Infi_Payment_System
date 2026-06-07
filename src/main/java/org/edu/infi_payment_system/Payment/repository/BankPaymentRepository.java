package org.edu.infi_payment_system.Payment.repository;

import jakarta.persistence.LockModeType;
import org.edu.infi_payment_system.Payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BankPaymentRepository
        extends JpaRepository<Payments, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
       SELECT p
       FROM BankPayment p
       WHERE p.paymentId = :paymentId
       """)
    Optional<Payments> findByPaymentIdForUpdate(
            UUID paymentId
    );

}
