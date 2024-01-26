package com.bank.account.infrastructure.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${tpd.topic-bank-account-update}")
    private String bankAccountUpdate;

    @Bean
    public NewTopic topicBankAccountUpdate() {
        return new NewTopic(bankAccountUpdate, 1, (short) 1);
    }
}
