package org.edu.infi_payment_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableKafka
@EnableScheduling
public class InfiPaymentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfiPaymentSystemApplication.class, args);
    }
    // http://localhost:8080/swagger-ui.html
}
/*

                                                ┌──────────┐
                                                │   User   │
                                                └────┬─────┘
                                                     │
                                            ┌────────▼────────┐
                                            │      Auth       │
                                            └────────┬────────┘
                                                     │
                                                     ▼
                             ┌─────────────────────────────────────────┐
                             │              Payment Flow               │
                             └─────────────────────────────────────────┘
                             Payment
                                │
                                ▼
                             FraudCheck
                                │
                                ▼
                             Kafka
                                │
                                ▼
                             Transaction
                                │
                             ┌──┼───────────────┬────────────┐
                             ▼  ▼               ▼            ▼
                            Account        Ledger       Audit
                             │                             │
                             ▼                             ▼
                            Cache                     Notification

                             ┌─────────────────────────────────────────┐
                             │              Refund Flow                │
                             └─────────────────────────────────────────┘

                             Refund
                                │
                                ▼
                             Transaction
                                ▼
                             Ledger
                                ▼
                             Audit
                                ▼
                             Notification

                             ┌─────────────────────────────────────────┐
                             │           Administration Flow           │
                             └─────────────────────────────────────────┘

                             Admin
                               │
                               ├── Users
                               ├── Payments
                               ├── Transactions
                               ├── Audits
                               └── Refunds

                             ┌─────────────────────────────────────────┐
                             │            Security Layer               │
                             └─────────────────────────────────────────┘

                             RateLimiting
                                   │
                                   ▼
                             All APIs

Core Fintech Engine

✅ account
✅ payment
✅ transaction
✅ ledger
✅ audit
✅ fraudCheck
✅ notification
✅ cache

Security Layer

✅ auth
✅ rateLimiting

Management Layer

✅ admin

Post-Payment Operations

✅ refund

Identity Layer

✅ user

 */
