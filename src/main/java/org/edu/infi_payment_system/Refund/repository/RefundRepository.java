package org.edu.infi_payment_system.Refund.repository;

import org.edu.infi_payment_system.Refund.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, String> {
    Optional<Refund> findByIdempotencyKey(String key);
}
