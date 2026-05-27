package org.edu.infi_payment_system.Refund.repository;

import jakarta.persistence.LockModeType;
import org.edu.infi_payment_system.Payment.entity.BankPayment;
import org.edu.infi_payment_system.Refund.entity.Refund;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefundRepository extends JpaRepository<Refund, UUID> {
    Optional<Refund> findByIdempotencyKey(
            String key
    );

    List<Refund> findByStatus(
            RefundStatus status
    );

    List<Refund> findByType(
            RefundType type
    );

    List<Refund> findByPayment_Id(
            UUID paymentId
    );

    @Query("""
            SELECT COALESCE(SUM(r.refundAmount), 0)
            FROM Refund r
            WHERE r.payment.paymentId = :paymentId
            AND r.status = 'SUCCESS'
            """)
    BigDecimal getTotalRefundedAmount(
            @Param("paymentId") UUID paymentId
    );
}
