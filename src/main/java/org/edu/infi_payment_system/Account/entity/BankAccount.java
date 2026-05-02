package org.edu.infi_payment_system.Account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.edu.infi_payment_system.Account.enums.AccountType;
import org.edu.infi_payment_system.User.entity.AppUser;
import org.edu.infi_payment_system.Account.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_accounts",
        indexes = @Index(name = "idx_last4", columnList = "last4Digits"))
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private AppUser user;

    @Column(nullable = false)
    private String bankName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType = AccountType.SAVINGS;

    @Column(nullable = false , unique = true)
    private String accountNumberEncrypted;

    @Column(nullable = false)
    private String last4Digits;

    @Column(nullable = false)
    private String ifscCode;

    @Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false,updatable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime blockedAt;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void UpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }
}
