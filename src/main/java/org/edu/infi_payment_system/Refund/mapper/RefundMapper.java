package org.edu.infi_payment_system.Refund.mapper;


import org.edu.infi_payment_system.Payment.entity.BankPayment;
import org.edu.infi_payment_system.Refund.dto.RefundRequestDto;
import org.edu.infi_payment_system.Refund.dto.RefundResponseDto;
import org.edu.infi_payment_system.Refund.entity.Refund;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;
import org.springframework.stereotype.Component;

@Component
public class RefundMapper {

    public Refund toEntity(RefundRequestDto dto,
                           BankPayment payment,
                           RefundType refundType

    ){
        if(dto == null){
            return null;
        }

        Refund refund = new Refund();
        refund.setPayment(payment);
        refund.setRefundAmount(dto.getRefundAmount());
        refund.setReason(dto.getReason());
        refund.setIdempotencyKey(dto.getIdempotencyKey());
        refund.setStatus(RefundStatus.PROCESSING);
        refund.setType(refundType);

        return refund;
    }
    public RefundResponseDto toResponseDto(Refund refund){
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
