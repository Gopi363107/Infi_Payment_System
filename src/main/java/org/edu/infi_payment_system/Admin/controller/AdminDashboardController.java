package org.edu.infi_payment_system.Admin.controller;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.DashboardResponse;
import org.edu.infi_payment_system.Admin.service.AdminDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
@RestController
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(
                dashboardService.getDashboard()
        );
    }
}
