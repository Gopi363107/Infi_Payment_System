package org.edu.infi_payment_system.FraudCheck.rule;

import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountLimitRule implements FraudRule{

    private static final BigDecimal MAX_LIMIT = new BigDecimal("50000");

    @Override
    public FraudCheckResult check(PaymentRequestDto request){

        if(request.getAmount().compareTo(MAX_LIMIT) > 0){
            return FraudCheckResult.fail(
                    "Amount exceeds allowed limit!"
            );
        }

        return FraudCheckResult.pass();
    }
}
