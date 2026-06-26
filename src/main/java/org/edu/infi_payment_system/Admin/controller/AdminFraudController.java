package org.edu.infi_payment_system.Admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.FraudCaseResponse;
import org.edu.infi_payment_system.Admin.dto.FraudStatsResponse;
import org.edu.infi_payment_system.Admin.dto.ResolveFraudRequest;
import org.edu.infi_payment_system.Admin.service.AdminFraudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/fraud")
@RequiredArgsConstructor
public class AdminFraudController {

    private final AdminFraudService adminFraudService;

    @GetMapping("/{fraudId}")
    public ResponseEntity<FraudCaseResponse> getFraudCase(
            @PathVariable UUID fraudId
    ) {
        return ResponseEntity.ok(adminFraudService.getFraudCase(fraudId));
    }

    @GetMapping
    public ResponseEntity<List<FraudCaseResponse>> getAllFraudCases() {
        return ResponseEntity.ok(adminFraudService.getAllFraudCases());
    }

    @PutMapping("/{fraudId}/resolve")
    public ResponseEntity<String> resolveFraudCase(
            @PathVariable UUID fraudId,
            @Valid @RequestBody ResolveFraudRequest request
    ) {

        adminFraudService.resolvedFraudCase(
                fraudId,
                request.getStatus(),
                request.getAdminRemarks()
        );

        return ResponseEntity.ok("Fraud case resolved successfully.");
    }

    @GetMapping("/stats")
    public ResponseEntity<FraudStatsResponse> getFraudStats() {
        return ResponseEntity.ok(adminFraudService.getFraudStats());
    }

}