package org.edu.infi_payment_system.Account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.edu.infi_payment_system.User.entity.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_accounts",
        indexes = @Index(name = "idx_account_number", columnList = "account_number"))
public class BankAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false , unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accountNumberEncrypted;

    @Column(nullable = false)
    private String accountNumberLast4;

    @Column(nullable = false)
    private String ifscCode;

    @Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Double balance = 0.0;
}
