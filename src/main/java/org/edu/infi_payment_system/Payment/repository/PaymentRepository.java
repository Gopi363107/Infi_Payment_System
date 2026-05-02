package org.edu.infi_payment_system.Payment.repository;

import org.edu.infi_payment_system.Payment.dto.PaymentResponseDto;
import org.edu.infi_payment_system.Payment.entity.BankPayment;
import org.edu.infi_payment_system.Payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<BankPayment, Long> {
    List<BankPayment>  findBySenderAccountIdOrReceiverAccountId(Long senderId, Long receiverId);
    List<BankPayment> findByPaymentStatus(PaymentStatus status);
}
