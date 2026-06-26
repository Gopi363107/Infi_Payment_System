package org.edu.infi_payment_system.Admin.service;

import org.edu.infi_payment_system.Admin.dto.FraudCaseResponse;
import org.edu.infi_payment_system.Admin.dto.FraudStatsResponse;
import org.edu.infi_payment_system.Admin.enums.FraudStatus;

import java.util.List;
import java.util.UUID;

public interface AdminFraudService {

    FraudCaseResponse getFraudCase(UUID fraudId);

    List<FraudCaseResponse> getAllFraudCases();

    void resolvedFraudCase(
            UUID fraudId,
            FraudStatus status,
            String remarks
    );

    FraudStatsResponse getFraudStats();

}
