package org.edu.infi_payment_system.FraudCheck.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FraudCheckResult {

    private boolean fraudulent;
    private String reason;

    public static FraudCheckResult pass(){
        return new FraudCheckResult(false , null );
    }

    public static FraudCheckResult fail(String reason){
        return new FraudCheckResult(true , reason);
    }
}
