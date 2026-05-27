package org.edu.infi_payment_system.Refund.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponseDto {

    private UUID refundId;
    private UUID paymentId;
    private BigDecimal refundAmount;
    private String reason;
    private RefundStatus status;
    private RefundType type;
}
