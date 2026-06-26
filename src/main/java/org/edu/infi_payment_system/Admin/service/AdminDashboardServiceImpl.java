package org.edu.infi_payment_system.Admin.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.DashboardResponse;
import org.edu.infi_payment_system.Admin.dto.RecentActivityResponse;
import org.edu.infi_payment_system.Admin.dto.TransactionStatsResponse;
import org.edu.infi_payment_system.Admin.dto.UserStatsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService{

    private final AdminUserService adminUserService;
    private final AdminTransactionService transactionService;
    private final AdminAuditService adminAuditService;

    @Override
    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        response.setUserStats(getUserStats());

        response.setTransactionStats(
                getTransactionStats()
        );

        response.setRecentActivities(
                getRecentActivities()
        );
        return response;
    }

    private TransactionStatsResponse getTransactionStats() {
        return transactionService.getTransactionStats();
    }

    private List<RecentActivityResponse> getRecentActivities() {

        return adminAuditService.getRecentActivities();
    }

    private UserStatsResponse getUserStats() {
        return adminUserService.getUserStats();
    }
}
