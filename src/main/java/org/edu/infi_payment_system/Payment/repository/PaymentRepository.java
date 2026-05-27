package org.edu.infi_payment_system.Payment.repository;

import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.entity.BankPayment;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<BankPayment, UUID> {
    List<BankPayment>  findBySenderAccountIdOrReceiverAccountId(Long senderId, Long receiverId);
    List<BankPayment> findByStatus(PaymentStatus status);

    Optional<BankPayment> findByPaymentId(
            @NotNull(message = "Payment id is required") UUID paymentId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
       SELECT p
       FROM BankPayment p
       WHERE p.paymentId = :paymentId
       """)
    Optional<BankPayment> findByPaymentIdForUpdate(
            @Param("paymentId") UUID paymentId
    );
}
