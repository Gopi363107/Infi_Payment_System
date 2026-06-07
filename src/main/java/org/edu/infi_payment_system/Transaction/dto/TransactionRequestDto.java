package org.edu.infi_payment_system.Transaction.dto;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDto {

    @NotNull(message = "Payment id is required")
    private UUID paymentId;

    @NotNull(message = "senderId is required")
    private UUID senderId;

    @NotNull(message = "receiver id is required")
    private UUID receiverId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater then zero")
    private BigDecimal amount;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
