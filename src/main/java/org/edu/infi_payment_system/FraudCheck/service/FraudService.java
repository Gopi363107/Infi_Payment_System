package org.edu.infi_payment_system.FraudCheck.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.FraudCheck.rule.FraudRule;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FraudService {

    private final List<FraudRule> fraudRules;

    public FraudCheckResult checkResult(PaymentRequestDto request){

        for(FraudRule rule : fraudRules){
            FraudCheckResult result = rule.check(request);

            if(result.isFraudulent()){
                return result;
            }
        }

        return FraudCheckResult.pass();
    }
}
