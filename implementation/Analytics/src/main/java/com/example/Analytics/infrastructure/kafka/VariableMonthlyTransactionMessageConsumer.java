package com.example.Analytics.infrastructure.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface VariableMonthlyTransactionMessageConsumer {
    void listenVariableMonthlyTransactionChange(ConsumerRecord<String, JsonNode> cr, JsonNode payload);

}
