package org.edu.infi_payment_system.Refund.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Refund.dto.RefundRequestDto;
import org.edu.infi_payment_system.Refund.dto.RefundResponseDto;
import org.edu.infi_payment_system.Refund.enums.RefundStatus;
import org.edu.infi_payment_system.Refund.enums.RefundType;
import org.edu.infi_payment_system.Refund.service.RefundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundResponseDto> createRefund(
            @Valid @RequestBody RefundRequestDto dto){

        RefundResponseDto response = refundService.createRefund(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<RefundResponseDto>> getByPaymentId(
            @PathVariable UUID paymentId){
        return ResponseEntity.ok(refundService.getByPaymentId(paymentId));
    }

    @GetMapping("/{refundId}")
    public ResponseEntity<RefundResponseDto> getByRefundId(
            @PathVariable UUID refundId){
        return ResponseEntity.ok(refundService.getByRefundId(refundId));
    }

    @GetMapping
    public ResponseEntity<List<RefundResponseDto>> getAllRefunds(){
        return ResponseEntity.ok(refundService.getAllRefunds());
    }

    @GetMapping("/status/{refundStatus}")
    public ResponseEntity<List<RefundResponseDto>> getByRefundStatus(
            @PathVariable RefundStatus refundStatus){
        return ResponseEntity.ok(refundService.getByRefundStatus(refundStatus));
    }

    @GetMapping("/type/{refundType}")
    public ResponseEntity<List<RefundResponseDto>> getByRefundType(
            @PathVariable RefundType refundType){
        return ResponseEntity.ok(refundService.getByRefundType(refundType));
    }
}
