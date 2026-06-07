package org.edu.infi_payment_system.Refund.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Payment.entity.Payments;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.edu.infi_payment_system.Payment.repository.BankPaymentRepository;
import org.edu.infi_payment_system.Refund.entity.Refunds;
import org.edu.infi_payment_system.Refund.exception.custom.PaymentIdNotFoundException;
import org.edu.infi_payment_system.Refund.dto.RefundRequestDto;
import org.edu.infi_payment_system.Refund.dto.RefundResponseDto;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;
import org.edu.infi_payment_system.Refund.exception.custom.PaymentNotSucceedException;
import org.edu.infi_payment_system.Refund.exception.custom.RefundIdNotFoundException;
import org.edu.infi_payment_system.Refund.exception.custom.RefundLimitExceededException;
import org.edu.infi_payment_system.Refund.mapper.RefundMapper;
import org.edu.infi_payment_system.Refund.repository.RefundRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService{

    private final RefundRepository refundRepository;
    private final BankPaymentRepository bankPaymentRepository;
    private final RefundMapper refundMapper;

    @Override
    @Transactional
    public RefundResponseDto createRefund(RefundRequestDto dto){
        Refunds existingRefund = refundRepository
                .findByIdempotencyKey(dto.getIdempotencyKey())
                .orElse(null);

        if(existingRefund != null){
            return refundMapper.toResponseDto(existingRefund);
        }

        Payments payment = bankPaymentRepository
                .findByPaymentIdForUpdate(dto.getPaymentId())
                .orElseThrow(() ->
                        new PaymentIdNotFoundException(
                                "PaymentId not found"
                        )
                );

        if(payment.getStatus() != PaymentStatus.SUCCESS){
            throw new PaymentNotSucceedException(
                    "Only successful payments can be refunded!"
            );
        }

        BigDecimal totalRefundedAmount =
                refundRepository.getTotalRefundedAmount(
                        dto.getPaymentId()
                );

        if(totalRefundedAmount == null){
            totalRefundedAmount = BigDecimal.ZERO;
        }
        BigDecimal totalAfterRefund =
                totalRefundedAmount.add(
                        dto.getRefundAmount()
                );


        if(totalAfterRefund.compareTo(payment.getAmount()) > 0){
            throw new RefundLimitExceededException(
                    "Refunds amount exceeds payment amount"
            );
        }

        RefundType refundType;

        if(totalAfterRefund.compareTo(payment.getAmount()) == 0){
            refundType = RefundType.FULL_REFUND;
        }
        else{
            refundType = RefundType.PARTIAL_REFUND;
        }

        Refunds refund = refundMapper.toEntity(
                dto,
                payment,
                refundType
        );

        Refunds saveRefund = refundRepository.save(refund);

        return refundMapper.toResponseDto(
                saveRefund
        );
    }

    @Override
    public RefundResponseDto getByRefundId(UUID refundId) {
        Refunds refund = refundRepository
                .findById(refundId)
                .orElseThrow(()-> new RefundIdNotFoundException(
                        "Refunds id is not found!")
                );

        return refundMapper.toResponseDto(refund);
    }

    @Override
    public List<RefundResponseDto> getByPaymentId(UUID paymentId) {
        return refundRepository
                .findByPayment_PaymentId(paymentId)
                .stream()
                .map(refundMapper :: toResponseDto)
                .toList();
    }

    @Override
    public List<RefundResponseDto> getByRefundStatus(RefundStatus status) {
        return refundRepository.findByStatus(status)
                .stream()
                .map(refundMapper :: toResponseDto)
                .toList();
    }

    @Override
    public List<RefundResponseDto> getByRefundType(RefundType type) {
        return refundRepository.findByType(type)
                .stream()
                .map(refundMapper :: toResponseDto)
                .toList();
    }

    @Override
    public List<RefundResponseDto> getAllRefunds() {
        return refundRepository.findAll()
                .stream()
                .map(refundMapper :: toResponseDto)
                .toList();
    }
}
