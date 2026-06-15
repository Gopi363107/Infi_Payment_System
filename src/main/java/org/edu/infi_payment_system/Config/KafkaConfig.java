package org.edu.infi_payment_system.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic paymentCompletedTopic(){
        return new NewTopic(
                "payment-completed",
                3,
                (short)1
        );
    }
}
