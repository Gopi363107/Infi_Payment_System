package org.edu.infi_payment_system.Audit.repository;

import org.edu.infi_payment_system.Audit.entity.AuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditLogs , UUID> {

}
