package org.edu.infi_payment_system.FraudCheck.rule;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VelocityRule implements FraudRule {

    private  final PaymentRepository paymentRepository;

    @Override
    public FraudCheckResult check(PaymentRequestDto request){

        long count = paymentRepository.countRecentTransactions(
                request.getSenderAccountId(),
                LocalDateTime.now().minusMinutes(1)
        );

        if(count >= 5){
            return FraudCheckResult.fail(
                "Too many transactions in one minute!"
            );
        }

        return FraudCheckResult.pass();
    }
}
