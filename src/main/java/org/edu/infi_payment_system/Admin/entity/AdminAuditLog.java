package org.edu.infi_payment_system.Admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.Admin.enums.AdminAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminDetails admin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminAction adminAction;

    private String targetId;

    private String description;

    private LocalDateTime timeStamp;
}
