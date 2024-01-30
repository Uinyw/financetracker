package com.financetracker.savingsgoal.infrastructure.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.openapitools.client.model.BankAccountDto;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@RequiredArgsConstructor
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, BankAccountDto> consumerFactory() {
        final JsonDeserializer<BankAccountDto> bankAccountDtoJsonDeserializer = new JsonDeserializer<>(BankAccountDto.class, false);
        bankAccountDtoJsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                bankAccountDtoJsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BankAccountDto> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, BankAccountDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
