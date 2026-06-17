package org.edu.infi_payment_system.Auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final RedisTemplate<String , Object> redisTemplate;

    private static final String PREFIX = "Blacklist:" ;

    public void BlacklistToken(String token ,
                               long expirationMillis){

        redisTemplate.opsForValue().set(
                PREFIX + token,
                "BLACKLISTED",
                Duration.ofMillis(expirationMillis)
        );

    }

    public boolean isBlackList(String token){

        return redisTemplate.hasKey(PREFIX+token);
    }
}
