package org.edu.infi_payment_system.Account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.edu.infi_payment_system.Account.enums.AccountType;
import org.edu.infi_payment_system.User.entity.Users;
import org.edu.infi_payment_system.Account.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_accounts", indexes = {
        @Index(name = "idx_last4", columnList = "last4Digits"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private Users user;

    @Column(nullable = false , length = 100)
    private String bankName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType = AccountType.SAVINGS;

    @Column(nullable = false)
    private String accountNumberEncrypted;

    @Column(nullable = false, unique = true)
    private String accountNumberHash;

    @Column(nullable = false  ,length = 4)
    private String last4Digits;

    @Column(nullable = false , length = 11)
    private String ifscCode;

    @Column(nullable = false , length = 100)
    private String accountHolderName;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime blockedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){this.updatedAt = LocalDateTime.now();}

    @PreUpdate
    public void updatedAt(){
        this.updatedAt = LocalDateTime.now();
    }
}
