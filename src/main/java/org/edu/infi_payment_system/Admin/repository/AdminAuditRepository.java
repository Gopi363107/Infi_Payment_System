package org.edu.infi_payment_system.Admin.repository;

import org.edu.infi_payment_system.Admin.entity.AdminAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdminAuditRepository extends JpaRepository<AdminAuditLog, UUID> {

    List<AdminAuditLog> findTop10ByOrderByCreatedAtDesc();
}
/*

Phase 1 Progress
✅ User Management
Get user details
Search users
Freeze user
Unfreeze user transaction history
User statistics

✅ Transaction Monitoring

Transaction details
Search transactions
Failed transactions
Transaction statistics
Pagination

✅ Audit Log

Recent activities
Audit entity
Audit repository
Audit service

✅ Dashboard

User statistics
Transaction statistics
Recent activities
 */
