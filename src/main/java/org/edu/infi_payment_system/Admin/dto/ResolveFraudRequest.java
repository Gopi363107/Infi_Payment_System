package org.edu.infi_payment_system.Admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.edu.infi_payment_system.Admin.enums.FraudStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolveFraudRequest {

    @NotNull(message = "Fraud status is required")
    private FraudStatus status;

    @NotBlank(message = "Admin remarks are required")
    private String adminRemarks;
}