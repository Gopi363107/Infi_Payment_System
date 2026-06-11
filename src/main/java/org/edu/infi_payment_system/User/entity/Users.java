package org.edu.infi_payment_system.User.entity;

import org.edu.infi_payment_system.Account.entity.Accounts;
import org.edu.infi_payment_system.User.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.edu.infi_payment_system.User.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_mobile", columnList = "mobileNumber")
})
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true , nullable = false, length = 100)
    private String email;

    @Column(nullable = false )
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Column(name = "is_verified", nullable = false)
    private Boolean verified = false;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Accounts> accounts;

    @Column(name = "mobile_number", unique = true, nullable = false, length = 15)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public Collection<?extends GrantedAuthority> getAuthorities(){
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    @Override
    public String getUsername(){
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return this.accountStatus != AccountStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return this.verified;
    }
}
