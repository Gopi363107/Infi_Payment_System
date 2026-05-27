package org.edu.infi_payment_system.Payment.service;


import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentResponseDto processPayment(PaymentRequestDto dto);
    PaymentResponseDto getPaymentById(UUID id);
    List<PaymentResponseDto> getPaymentByAccount(Long accountId);
    List<PaymentResponseDto> getAllPayments();
    List<PaymentResponseDto> getPaymentByStatus(PaymentStatus status);
}
