package org.edu.infi_payment_system.Refund.service;

import org.edu.infi_payment_system.Refund.dto.RefundRequestDto;
import org.edu.infi_payment_system.Refund.dto.RefundResponseDto;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;

import java.util.List;
import java.util.UUID;

public interface RefundService {

    RefundResponseDto createRefund(RefundRequestDto dto);
    RefundResponseDto getByRefundId(UUID refundId);
    List<RefundResponseDto> getByPaymentId(UUID paymentId);
    List<RefundResponseDto> getAllRefunds();
    List<RefundResponseDto> getByRefundStatus(RefundStatus status);
    List<RefundResponseDto> getByRefundType(RefundType type);
}
