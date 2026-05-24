package org.edu.infi_payment_system.Refund.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.Payment.entity.BankPayment;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refunds" , indexes = {
                @Index(name = "idx_payment_id" , columnList = "payment_id"),
                @Index(name = "idx_refund_status" , columnList = "status"),
                @Index(name = "idx_refund_type" , columnList = "type")
        })
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID refundId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private BankPayment payment;

    @Column(nullable = false)
    @Positive
    private BigDecimal refundAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RefundType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RefundStatus status;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
