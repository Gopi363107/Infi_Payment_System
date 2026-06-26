package org.edu.infi_payment_system.Admin.service;

import org.edu.infi_payment_system.Admin.dto.RecentActivityResponse;

import java.util.List;

public interface AdminAuditService {

    List<RecentActivityResponse> getRecentActivities();
}
