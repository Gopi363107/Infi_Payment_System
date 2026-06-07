package org.edu.infi_payment_system.User.repository;

import org.edu.infi_payment_system.User.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
    
    Optional<Users> findByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);
}
