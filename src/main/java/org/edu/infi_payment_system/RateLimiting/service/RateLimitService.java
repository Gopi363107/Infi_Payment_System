package org.edu.infi_payment_system.RateLimiting.service;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final Bucket bucket;

    public boolean allowRequest(){
        return bucket.tryConsume(1);
    }
}
