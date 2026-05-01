package org.edu.infi_payment_system.Account.repository;

import org.edu.infi_payment_system.Account.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount , Long> {
    boolean existsByUserId(Long userId);
}
