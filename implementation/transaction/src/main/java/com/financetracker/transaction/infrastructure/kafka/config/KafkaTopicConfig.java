package com.financetracker.transaction.infrastructure.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${tpd.topic-oneTimeTransaction-update}")
    private String oneTimeTransaction;

    @Value("${tpd.topic-recurringTransaction-update}")
    private String recurringTransaction;

    @Bean
    public NewTopic oneTimeTransactionUpdate() {
        return new NewTopic(oneTimeTransaction, 1, (short) 1);
    }

    @Bean
    public NewTopic recurringTransactionUpdate() {
        return new NewTopic(recurringTransaction, 1, (short) 1);
    }
}
