package org.edu.infi_payment_system.Transaction.mapper;

import lombok.Getter;
import org.edu.infi_payment_system.Transaction.dto.TransactionRequestDto;
import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;
import org.edu.infi_payment_system.Transaction.entity.Transactions;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TransactionMapper {

    public Transactions toEntity(TransactionRequestDto dto){

        if(dto == null)return null;

        Transactions transaction = new Transactions();

        transaction.setPaymentId(dto.getPaymentId());
        transaction.setSenderId(dto.getSenderId());
        transaction.setReceiverId(dto.getReceiverId());
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionStatus(TransactionStatus.PROCESSING);
        transaction.setCreatedAt(dto.getCreatedAt());

        return transaction;
    }

    public TransactionResponseDto toResponseDto(Transactions payment){
        if(payment == null)return null;

        TransactionResponseDto response = new TransactionResponseDto();

        response.setTransactionId(payment.getTransactionId());
        response.setPaymentId(payment.getPaymentId());
        response.setSenderId(payment.getSenderId());
        response.setReceiverId(payment.getReceiverId());
        response.setTransactionStatus(payment.getTransactionStatus());
        response.setAmount(payment.getAmount());
        response.setCreatedAt(payment.getCreatedAt());
        response.setCompletedAt(payment.getCompletedAt());

        return response;
    }
}
