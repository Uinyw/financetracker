package com.example.Analytics.infrastructure.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface FixedMonthlyTransactionMessageConsumer {
    void listenFixedMonthlyTransactionChange(ConsumerRecord<String, JsonNode> cr, JsonNode payload);

}
