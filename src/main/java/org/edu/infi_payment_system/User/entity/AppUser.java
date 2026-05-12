package org.edu.infi_payment_system.User.entity;

import org.edu.infi_payment_system.Account.entity.BankAccount;
import org.edu.infi_payment_system.User.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.edu.infi_payment_system.User.enums.UserRole;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_mobile", columnList = "mobileNumber")
})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true , nullable = false, length = 100)
    private String email;

    @Column(nullable = false )
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BankAccount> accounts;

    @Column(unique = true , nullable = false , length = 15)
    private String mobileNumber;

    @Column(nullable = false)
    private boolean verified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
