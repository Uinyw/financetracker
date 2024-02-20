package com.example.Analytics.Kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.ProductDto;

public interface MessageConsumer {
    void listenBankAccountChange(ConsumerRecord<String, ProductDto> cr, ProductDto payload);

}
