package org.edu.infi_payment_system.Payment.mapper;

import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.entity.BankPayment;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentMapper {

    public BankPayment toEntity(PaymentRequestDto dto){
        if(dto == null)return null;

        BankPayment payment = new BankPayment();
        payment.setSenderAccountId(dto.getSenderAccountId());
        payment.setReceiverAccountId(dto.getReceiverAccountId());
        payment.setAmount(dto.getAmount());

        // Default values
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        return payment;
    }

    public PaymentResponseDto toResponseDto(BankPayment payment) {

        if (payment == null) {
            return null;
        }

        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setTransactionId(payment.getId());
        dto.setSenderAccountId(payment.getSenderAccountId());
        dto.setReceiverAccountId(payment.getReceiverAccountId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setCompletedAt(payment.getCompletedAt());

        return dto;
    }
}
