package org.edu.infi_payment_system.FraudCheck.rule;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.repository.PaymentRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicatePaymentRule implements FraudRule{

    private final PaymentRepository paymentRepository;

    @Override
    public FraudCheckResult check(PaymentRequestDto request){
        boolean exists = paymentRepository.existsBySenderAccountIdAndReceiverAccountIdAndAmount(
                request.getSenderAccountId(),
                request.getReceiverAccountId(),
                request.getAmount()
        );

        if(exists){
            return FraudCheckResult.fail(
                "Duplicate payment Detected!"
            );
        }

        return FraudCheckResult.pass();
    }
}
