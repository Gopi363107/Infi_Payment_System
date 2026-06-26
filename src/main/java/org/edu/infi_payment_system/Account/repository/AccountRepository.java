package org.edu.infi_payment_system.Account.repository;

import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.edu.infi_payment_system.Account.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, UUID> {
    boolean existsByUserId(UUID userId);

    Optional<Object> findByAccountId(@NotNull(message = "receiver id is required") UUID receiverAccountId);

    // pessimistic lock is used to solve race condition in concurrency control
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Accounts a WHERE a.accountId = :accountId")
    Optional<Accounts> findByAccountIdForUpdate(@NotNull(message = "Id is required") UUID accountId);


}
