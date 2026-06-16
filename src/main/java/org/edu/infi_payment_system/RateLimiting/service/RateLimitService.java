package org.edu.infi_payment_system.RateLimiting.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Config.Bucket4jConfig;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final Bucket4jConfig bucket4jConfig;

    private final Map<String , Bucket> cache = new ConcurrentHashMap<>();

    public boolean tryConsume(
            String key,
            String endPoint
    ){
        Bucket bucket = cache.computeIfAbsent(
                key,
                k->createBucket(endPoint)
        );

        return bucket.tryConsume(1);
    }

    private Bucket createBucket(String endPoint){

        Bandwidth bandwidth;

        if(endPoint.contains("/login")){
            bandwidth = bucket4jConfig.paymentLimit();
        }
        else if(endPoint.contains("/payments")){
            bandwidth = bucket4jConfig.paymentLimit();
        }
        else {
            bandwidth = bucket4jConfig.paymentLimit();
        }

        return Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }
}
