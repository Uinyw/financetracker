package com.example.Analytics.budgetFunctionality.infrastructure.kafka;

import com.example.Analytics.budgetFunctionality.logic.operations.BudgetService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.RecurringTransactionDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaMessageConsumer implements MessageConsumer {

    private final BudgetService budgetService;

    @KafkaListener(topics = "oneTimeTransaction-update", groupId = "transaction")
    public void listenOneTimeTransactionChange(ConsumerRecord<String, OneTimeTransactionDto> cr, @Payload OneTimeTransactionDto payload) {

    }

    @KafkaListener(topics = "recurringTransaction-update", groupId = "transaction")
    public void listenRecurringTransactionChange(ConsumerRecord<String, RecurringTransactionDto> cr, @Payload RecurringTransactionDto payload) {

    }
}
