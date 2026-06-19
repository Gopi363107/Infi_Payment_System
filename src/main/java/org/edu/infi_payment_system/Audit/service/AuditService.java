package org.edu.infi_payment_system.Audit.service;


/*
1. Audit Trail
2. Logging
3. Retry Mechanism

Reason:

-> Audit tells us what happened in the payment system.
-> Logging tells us why it happened.
-> Retry handles temporary failures.
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.infi_payment_system.Audit.entity.AuditLogs;
import org.edu.infi_payment_system.Audit.enums.AuditAction;
import org.edu.infi_payment_system.Audit.repository.AuditRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAudit(UUID paymentId,
                          AuditAction action,
                          UUID senderId,
                          UUID receiverId){

        AuditLogs log = new AuditLogs();
        log.setPaymentId(paymentId);
        log.setAction(action);
        log.setSenderId(senderId);
        log.setReceiverId(receiverId);
        log.setTimeStamp(LocalDateTime.now());

        auditRepository.save(log);
    }
}
