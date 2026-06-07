package org.edu.infi_payment_system.Refund.repository;

import org.edu.infi_payment_system.Refund.entity.Refunds;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefundRepository extends JpaRepository<Refunds, UUID> {
    Optional<Refunds> findByIdempotencyKey(
            String key
    );

    List<Refunds> findByStatus(
            RefundStatus status
    );

    List<Refunds> findByType(
            RefundType type
    );

    List<Refunds> findByPayment_PaymentId(
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
