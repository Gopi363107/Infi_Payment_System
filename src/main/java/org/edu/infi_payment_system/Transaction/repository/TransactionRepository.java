package org.edu.infi_payment_system.Transaction.repository;

import org.edu.infi_payment_system.Transaction.entity.Transactions;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transactions, UUID> {
    Optional<Transactions> findByPaymentId(UUID paymentId);

    Optional<Transactions> findByTransactionId(UUID transactionId);

    Collection<Transactions> findByTransactionStatus(TransactionStatus status);

    List<Transactions> findBySenderIdOrReceiverId(UUID senderId , UUID receiverId);

    List<Transactions> findBySenderId(UUID senderId);
}
