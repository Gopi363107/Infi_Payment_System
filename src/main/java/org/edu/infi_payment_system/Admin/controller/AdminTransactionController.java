package org.edu.infi_payment_system.Admin.controller;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.TransactionDetailsResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionSearchResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionStatsResponse;
import org.edu.infi_payment_system.Admin.service.AdminTransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/transaction")
@RequiredArgsConstructor
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailsResponse>
                        getTransaction(@PathVariable UUID transactionId){

        return ResponseEntity.ok(adminTransactionService.getTransaction(transactionId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TransactionSearchResponse>> searchTransactions(
            @RequestParam(required = false) UUID senderId,
            @RequestParam(required = false) UUID receiverId,
            @RequestParam(required = false) String status){
        return ResponseEntity.ok(adminTransactionService.searchTransactions(senderId,receiverId,status));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionSearchResponse>> getAllTransactions(Pageable pageable){

        return ResponseEntity.ok(adminTransactionService.getAllTransactions(pageable));
    }

    @GetMapping("/failed")
    public ResponseEntity<List<TransactionSearchResponse>> failedTransactions(){

        return ResponseEntity.ok(adminTransactionService.failedTransactions());
    }

    @GetMapping("/stats")
    public ResponseEntity<TransactionStatsResponse> getTransactionStats(){
        return  ResponseEntity.ok(adminTransactionService.getTransactionStats());
    }
}
