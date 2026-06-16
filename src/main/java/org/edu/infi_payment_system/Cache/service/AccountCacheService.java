package org.edu.infi_payment_system.Cache.service;

import lombok.RequiredArgsConstructor;
import org.edu.infi_payment_system.Account.entity.Accounts;
import org.edu.infi_payment_system.Cache.constants.RedisKeys;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration CACHE_TTL =
            Duration.ofMinutes(30);

    public void save(Accounts account) {

        redisTemplate.opsForValue().set(
                RedisKeys.ACCOUNT_PREFIX + account.getAccountId(),
                account,
                CACHE_TTL
        );
    }

    public Accounts get(UUID accountId) {

        return (Accounts) redisTemplate
                .opsForValue()
                .get(RedisKeys.ACCOUNT_PREFIX + accountId);
    }

    public void evict(Long accountId) {

        redisTemplate.delete(
                RedisKeys.ACCOUNT_PREFIX + accountId
        );
    }
}