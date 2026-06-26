package org.edu.infi_payment_system.Admin.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.FraudCaseResponse;
import org.edu.infi_payment_system.Admin.dto.FraudStatsResponse;
import org.edu.infi_payment_system.Admin.entity.FraudCase;
import org.edu.infi_payment_system.Admin.enums.FraudStatus;
import org.edu.infi_payment_system.Admin.exception.custom.ResourceNotFoundException;
import org.edu.infi_payment_system.Admin.repository.FraudCaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminFraudServiceImpl implements AdminFraudService{

    private final FraudCaseRepository fraudCaseRepository;

    @Override
    public FraudCaseResponse getFraudCase(UUID fraudId) {

        FraudCase fraudCase = fraudCaseRepository.findByFraudId(fraudId)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud case not found."));

        return mapToFraudCaseResponse(fraudCase);
    }

    @Override
    public List<FraudCaseResponse> getAllFraudCases() {

        return fraudCaseRepository.findAll()
                .stream()
                .map(this :: mapToFraudCaseResponse)
                .toList();
    }

    @Override
    public FraudStatsResponse getFraudStats() {

        List<FraudCase> fraudCases = fraudCaseRepository.findAll();

        long totalCases = fraudCases.size();

        long pendingCases = fraudCases.stream()
                .filter(f -> f.getFraudStatus() == FraudStatus.PENDING)
                .count();

        long investigatingCases = fraudCases.stream()
                .filter(f -> f.getFraudStatus() == FraudStatus.INVESTIGATING)
                .count();

        long confirmedCases = fraudCases.stream()
                .filter(f -> f.getFraudStatus() == FraudStatus.CONFIRMED)
                .count();

        long rejectedCases = fraudCases.stream()
                .filter(f -> f.getFraudStatus() == FraudStatus.REJECTED)
                .count();

        BigDecimal totalFraudAmount = fraudCaseRepository.getTotalFraudAmount();

        double averageRiskScore = fraudCases.stream()
                .mapToInt(FraudCase::getRiskScore)
                .average()
                .orElse(0);

        int highestRiskScore = fraudCases.stream()
                .mapToInt(FraudCase::getRiskScore)
                .max()
                .orElse(0);

        return FraudStatsResponse.builder()
                .totalFraudCases(totalCases)
                .pendingCases(pendingCases)
                .investigatingCases(investigatingCases)
                .confirmedFraudCases(confirmedCases)
                .rejectedCases(rejectedCases)
                .totalFraudAmount(totalFraudAmount)
                .preventedLossAmount(BigDecimal.ZERO)
                .recoveredAmount(BigDecimal.ZERO)
                .averageRiskScore(averageRiskScore)
                .highestRiskScore(highestRiskScore)
                .build();
    }

    @Override
    public void resolvedFraudCase(UUID fraudId,
                                  FraudStatus status,
                                  String remarks) {

        FraudCase fraudCase = fraudCaseRepository.findByFraudId(fraudId)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud case not found."));

        fraudCase.setFraudStatus(status);
        fraudCase.setRemarks(remarks);
        fraudCase.setResolvedAt(LocalDateTime.now());

        fraudCaseRepository.save(fraudCase);
    }

    private FraudCaseResponse mapToFraudCaseResponse(FraudCase fraudCase) {

        return FraudCaseResponse.builder()
                .fraudCaseId(fraudCase.getFraudId())
                .transactionId(fraudCase.getTransaction().getTransactionId())
                .userId(fraudCase.getUserId())
                .fraudReason(fraudCase.getReason())
                .riskScore(fraudCase.getRiskScore())
                .status(fraudCase.getFraudStatus())
                .reviewedBy(fraudCase.getReviewedBy())
                .remarks(fraudCase.getRemarks())
                .detectedAt(fraudCase.getCreatedAt())
                .reviewedAt(fraudCase.getResolvedAt())
                .build();
    }
}
