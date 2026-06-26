package org.edu.infi_payment_system.Transaction.repository;

import org.edu.infi_payment_system.Transaction.entity.Transactions;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transactions, UUID> {
    Optional<Transactions> findByPaymentId(UUID paymentId);

    Optional<Transactions> findByTransactionId(UUID transactionId);

    List<Transactions> findByTransactionStatus(TransactionStatus status);

    List<Transactions> findBySenderIdOrReceiverId(UUID senderId , UUID receiverId);

    long countByTransactionStatus(
            TransactionStatus status
    );
    List<Transactions> findBySenderId(UUID senderId);

    List<Transactions> findByReceiverId(UUID receiverId);

    @Query("""
       SELECT COALESCE(SUM(t.amount), 0)
       FROM Transactions t
       WHERE t.transactionStatus = 'SUCCESS'
       """)
    BigDecimal getTotalAmountTransferred();

    List<Transactions> findBySenderIdOrReceiverIdOrTransactionStatus(
            UUID senderId, UUID receiverId, TransactionStatus transactionStatus);
}
