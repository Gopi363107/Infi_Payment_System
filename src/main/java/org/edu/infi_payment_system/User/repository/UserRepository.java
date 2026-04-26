package org.edu.infi_payment_system.User.repository;

import org.edu.infi_payment_system.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
