package org.edu.infi_payment_system.Payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.edu.infi_payment_system.Payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/transfer")
    public ResponseEntity<PaymentResponseDto> processPayment(
            @Valid @RequestBody PaymentRequestDto dto){
        PaymentResponseDto response = paymentService.processPayment(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(
             @PathVariable Long id){
        PaymentResponseDto response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentByAccount(
             @PathVariable Long accountId){
        return ResponseEntity.ok(paymentService.getPaymentByAccount(accountId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments(){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentByStatus(
            @PathVariable PaymentStatus status){
        return ResponseEntity.ok(paymentService.getPaymentByStatus(status));
    }
}
