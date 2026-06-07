package org.edu.infi_payment_system.Payment.mapper;

import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.entity.Payments;
import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionRequestMapper {

    public TransactionRequestDto toRequest(
            Payments payment,
            PaymentRequestDto paymentRequest) {

        TransactionRequestDto dto =
                new TransactionRequestDto();

        dto.setPaymentId(payment.getPaymentId());
        dto.setSenderId(paymentRequest.getSenderAccountId());
        dto.setReceiverId(paymentRequest.getReceiverAccountId());
        dto.setAmount(paymentRequest.getAmount());

        return dto;
    }
}
