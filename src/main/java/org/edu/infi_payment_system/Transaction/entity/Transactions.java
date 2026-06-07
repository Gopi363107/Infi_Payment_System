package org.edu.infi_payment_system.Transaction.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false , unique = true)
    private UUID transactionId;

    @Column(nullable = false)
    private UUID paymentId;

    @Column(nullable = false)
    private UUID senderId;

    @Column(nullable = false)
    private UUID receiverId;

    @Positive
    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus transactionStatus;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    @PreUpdate
    public void completedAt(){
        this.completedAt = LocalDateTime.now();
    }

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }
}
