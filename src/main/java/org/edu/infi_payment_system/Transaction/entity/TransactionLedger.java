package org.edu.infi_payment_system.Transaction.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.edu.infi_payment_system.Transaction.enums.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_ledger" ,indexes = {
    @Index(name = "idx_account_id" , columnList = "accountId"),
    @Index(name = "idx_transaction_id" , columnList = "transactionId"),
    @Index(name = "idx_reference_id" , columnList = "referenceId"),
    @Index(name = "idx_idempotency_key" , columnList = "idempotencyKey"),
        @Index(name = "idx_pair_entry_id", columnList = "pairEntryId")
})
public class TransactionLedger {

    // Identity & Traceability
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long transactionId;

    @Column(nullable = false)
    private Long referenceId;

    private Long traceId;

    // Account Information
    @Column(nullable = false)
    private Long accountId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private TransactionAccountType accountType;

    //Transaction Nature
    @Enumerated(EnumType.STRING)
    private TransactionEntryType entryType;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionChannel transactionChannel;


    //Money Details
    @Positive
    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionCurrency transactionCurrency;

    // Balance Snapshot
    private Double openingBalance;
    private Double closingBalance;

    //Double Entry Linking
    private String pairEntryId;

    //Transfer Details
    @Column(nullable = false)
    private Long fromAccountId;

    @Column(nullable = false)
    private Long toAccountId;

    //Status & Failure
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String failureReason;

    //Idempotency
    @Column(unique = true)
    private String idempotencyKey;

    //Metadata
    private String remarks;
    private String deviceId;
    private String location;

    //Time Fields
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    @PrePersist
    public void PrePersist(){
        createdAt = LocalDateTime.now();
    }


}
