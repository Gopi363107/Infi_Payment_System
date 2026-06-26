package org.edu.infi_payment_system.Admin.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Admin.dto.RecentActivityResponse;
import org.edu.infi_payment_system.Admin.entity.AdminAuditLog;
import org.edu.infi_payment_system.Admin.repository.AdminAuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAuditServiceImpl implements AdminAuditService{

    private final AdminAuditRepository adminAuditRepository;

    @Override
    public List<RecentActivityResponse> getRecentActivities() {
        return adminAuditRepository
                .findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this :: mapToRecentActivityResponse)
                .toList();
    }

    private RecentActivityResponse mapToRecentActivityResponse(AdminAuditLog audit) {

        RecentActivityResponse response = new RecentActivityResponse();

        response.setAuditId(audit.getAuditId());
        response.setAdminEmail(audit.getAdminEmail());
        response.setAction(audit.getAdminAction().name());
        response.setDescription(audit.getDescription());
        response.setTargetUserId(audit.getTargetUserId());
        response.setCreatedAt(audit.getCreatedAt());

        return response;
    }
}
