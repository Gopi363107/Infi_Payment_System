package org.edu.infi_payment_system.Refund.mapper;


import org.edu.infi_payment_system.Payment.entity.Payments;
import org.edu.infi_payment_system.Refund.dto.RefundRequestDto;
import org.edu.infi_payment_system.Refund.dto.RefundResponseDto;
import org.edu.infi_payment_system.Refund.entity.Refunds;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;
import org.springframework.stereotype.Component;

@Component
public class RefundMapper {

    public Refunds toEntity(RefundRequestDto dto,
                            Payments payment,
                            RefundType refundType

    ){
        if(dto == null){
            return null;
        }

        Refunds refund = new Refunds();
        refund.setPayment(payment);
        refund.setRefundAmount(dto.getRefundAmount());
        refund.setReason(dto.getReason());
        refund.setIdempotencyKey(dto.getIdempotencyKey());
        refund.setStatus(RefundStatus.PROCESSING);
        refund.setType(refundType);

        return refund;
    }
    public RefundResponseDto toResponseDto(Refunds refund){
        if(refund == null){
            return null;
        }

        RefundResponseDto response = new RefundResponseDto();
        response.setRefundId(refund.getRefundId());
        response.setPaymentId(refund.getPayment().getPaymentId());
        response.setRefundAmount(refund.getRefundAmount());
        response.setStatus(refund.getStatus());
        response.setType(refund.getType());
        response.setReason(refund.getReason());

        return response;
    }
}
