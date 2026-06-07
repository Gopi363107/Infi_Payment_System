package org.edu.infi_payment_system.Payment.repository;

import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.edu.infi_payment_system.Payment.entity.Payments;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payments, UUID> {
    List<Payments>  findBySenderAccountIdOrReceiverAccountId(UUID senderId, UUID receiverId);
    List<Payments> findByStatus(PaymentStatus status);

    Optional<Payments> findByPaymentId(
            @NotNull(message = "Payment id is required") UUID paymentId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
       SELECT p
       FROM BankPayment p
       WHERE p.paymentId = :paymentId
       """)
    Optional<Payments> findByPaymentIdForUpdate(
            @Param("paymentId") UUID paymentId
    );

    Payments findByIdempotencyKey(@NotNull(message = "idempotency key is required") UUID idempotencyKey);

    boolean existsBySenderAccountIdAndReceiverAccountIdAndAmount(
            UUID senderAccountId,
            UUID receiverAccountId,
            BigDecimal amount
    );

    @Query("""
SELECT COUNT(p)
FROM Payments p
WHERE p.senderAccountId = :senderId
AND p.createdAt >= :time
""")
    long countRecentTransactions(
            UUID senderId,
            LocalDateTime time
    );

    @Query("""
       SELECT COUNT(p)
       FROM Payments p
       WHERE p.senderAccountId = :accountId
       AND p.status = org.edu.infi_payment_system.Payment.enums.PaymentStatus.FAILED
       """)
    long countFailedPayments(UUID accountId);

    UUID getPaymentId();
}
