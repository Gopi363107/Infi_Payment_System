package org.edu.infi_payment_system.Admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.edu.infi_payment_system.Admin.enums.AdminAction;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private String targetUserId;

    private String targetType;

    private String oldValue;

    private String newValue;

    private String description;

    private boolean success;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
