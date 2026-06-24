package org.edu.infi_payment_system.User.repository;

import org.edu.infi_payment_system.User.entity.Users;
import org.edu.infi_payment_system.User.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    boolean existsByEmail(String email);
    
    Optional<Users> findByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    List<Users> findByNameContainingIgnoreCase(String name);

    List<Users> findByAccountStatus(AccountStatus status);

    List<Users> findByEmailContainingIgnoreCase(String email);
}
