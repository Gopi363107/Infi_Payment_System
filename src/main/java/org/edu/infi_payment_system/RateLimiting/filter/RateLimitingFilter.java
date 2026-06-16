package org.edu.infi_payment_system.RateLimiting.filter;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.RateLimiting.service.RateLimitService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(){

    }
}
