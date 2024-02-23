package com.example.Analytics.dietFunctionality.infrastructure.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.ProductDto;

public interface ProductMessageConsumer {
    void listenProductChange(ConsumerRecord<String, JsonNode> cr, JsonNode payload);

}
