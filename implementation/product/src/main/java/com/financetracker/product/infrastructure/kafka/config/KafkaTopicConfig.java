package com.financetracker.product.infrastructure.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${tpd.topic-product-update}")
    private String productUpdate;

    @Bean
    public NewTopic topicProductUpdate() {
        return new NewTopic(productUpdate, 1, (short) 1);
    }
}
