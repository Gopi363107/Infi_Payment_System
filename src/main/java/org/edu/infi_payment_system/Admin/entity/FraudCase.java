package org.edu.infi_payment_system.Admin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.edu.infi_payment_system.Admin.enums.FraudStatus;
import org.edu.infi_payment_system.Transaction.entity.Transactions;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FraudCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID fraudId;

    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transactions transaction;

    private String reason;

    private int riskScore;

    @Enumerated(EnumType.STRING)
    private FraudStatus fraudStatus;

    private UUID reviewedBy;

    private String remarks;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;
}
