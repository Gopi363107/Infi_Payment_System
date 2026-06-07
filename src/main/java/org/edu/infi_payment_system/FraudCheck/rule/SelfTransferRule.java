package org.edu.infi_payment_system.FraudCheck.rule;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SelfTransferRule implements FraudRule{

    @Override
    public FraudCheckResult check(PaymentRequestDto request){

        if(request.getSenderAccountId().equals(request.getReceiverAccountId())){
            return FraudCheckResult.fail(
              "Sender and receiver account id cannot be same!"
            );
        }

        return FraudCheckResult.pass();
    }
}
