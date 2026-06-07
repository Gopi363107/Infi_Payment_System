package org.edu.infi_payment_system.Payment.mapper;

import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.entity.Payments;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class PaymentMapper {

    public Payments toEntity(PaymentRequestDto dto){
        if(dto == null)return null;

        Payments payment = new Payments();
        payment.setSenderAccountId(dto.getSenderAccountId());
        payment.setReceiverAccountId(dto.getReceiverAccountId());
        payment.setAmount(dto.getAmount());

        // Default values
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        return payment;
    }

    public PaymentResponseDto toResponseDto(Payments payment) {

        if (payment == null) {
            return null;
        }

        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setTransactionId(payment.getPaymentId());
        dto.setSenderAccountId(payment.getSenderAccountId());
        dto.setReceiverAccountId(payment.getReceiverAccountId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setCompletedAt(payment.getCompletedAt());

        return dto;
    }
}
