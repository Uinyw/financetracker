package com.example.Analytics.budgetFunctionality.infrastructure.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.ProductDto;
import org.openapitools.client.model.RecurringTransactionDto;

public interface MessageConsumer {
    void listenOneTimeTransactionChange(ConsumerRecord<String, OneTimeTransactionDto> cr, OneTimeTransactionDto payload);
    void listenRecurringTransactionChange(ConsumerRecord<String, RecurringTransactionDto> cr, RecurringTransactionDto payload);
    //TODO this code is probably wrong -> how to get the node data
}
