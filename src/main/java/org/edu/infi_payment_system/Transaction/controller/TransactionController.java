package org.edu.infi_payment_system.Transaction.controller;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Transaction.dto.TransactionResponseDto;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.edu.infi_payment_system.Transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/paymentId/{paymentId}")
    public ResponseEntity<TransactionResponseDto>
        getByPaymentId(@PathVariable UUID paymentId){
        return ResponseEntity.ok(transactionService.getByPaymentId(paymentId));
    }

    @GetMapping("/transactionId/{transactionId}")
    public ResponseEntity<TransactionResponseDto>
        getByTransactionId(@PathVariable UUID transactionId){
        return ResponseEntity.ok(transactionService.getByTransactionId(transactionId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionResponseDto>>
    getByTransactionStatus(@PathVariable TransactionStatus status){
        return ResponseEntity.ok(transactionService.getByTransactionStatus(status));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAllTransaction(){
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }
}
