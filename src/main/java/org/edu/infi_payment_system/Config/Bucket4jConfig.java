package org.edu.infi_payment_system.Config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Bucket4jConfig {

    public Bandwidth loginLimit() {
        return Bandwidth.classic(
                5,
                Refill.greedy(5, Duration.ofMinutes(1))
        );
    }

    public Bandwidth paymentLimit() {
        return Bandwidth.classic(
                20,
                Refill.greedy(20, Duration.ofMinutes(1))
        );
    }

    public Bandwidth defaultLimit() {
        return Bandwidth.classic(
                100,
                Refill.greedy(100, Duration.ofMinutes(1))
        );
    }
}
