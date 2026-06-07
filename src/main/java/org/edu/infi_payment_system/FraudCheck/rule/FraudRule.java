package org.edu.infi_payment_system.FraudCheck.rule;

import org.edu.infi_payment_system.FraudCheck.dto.FraudCheckResult;
import org.edu.infi_payment_system.Payment.dto.PaymentRequestDto;

public interface FraudRule {

    FraudCheckResult check(PaymentRequestDto request);
}
