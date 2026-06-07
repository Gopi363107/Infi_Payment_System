package org.edu.infi_payment_system.FraudCheck.rule;


import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;
import org.edu.infi_payment_system.Payment.repository.PaymentRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlacklistRule implements FraudRule{

        private final PaymentRepository paymentRepository;

        private static final long FINAL_PAYMENT_THRESHOLD = 10;

        @Override
        public FraudCheckResult check(PaymentRequestDto request){

            long failedCount = paymentRepository.countFailedPayments(
                    request.getSenderAccountId()
            );

            if(failedCount >= FINAL_PAYMENT_THRESHOLD){
                return FraudCheckResult.fail(
                  "Account is temporarily blacklisted!"
                );
            }

            return FraudCheckResult.pass();
        }
}
