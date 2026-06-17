package org.edu.infi_payment_system.Cache.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Transaction.enums.TransactionStatus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionCacheService {

    private final RedisTemplate<String , Object> redisTemplate;

    private final static String PREFIX = "txn:";

    public void save(UUID transactionId,
                     TransactionStatus status) {

        redisTemplate.opsForValue().set(
                PREFIX + transactionId,
                status,
                Duration.ofMinutes(30)
        );
    }

    public TransactionStatus get(UUID transactionId) {

        return (TransactionStatus) redisTemplate
                .opsForValue()
                .get(PREFIX + transactionId);
    }

    public void delete(UUID transactionId) {

        redisTemplate.delete(
                PREFIX + transactionId
        );
    }


}
