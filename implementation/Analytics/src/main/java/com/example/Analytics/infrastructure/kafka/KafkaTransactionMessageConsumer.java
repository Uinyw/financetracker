package com.example.Analytics.infrastructure.kafka;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.operations.BudgetService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.RecurringTransactionDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaTransactionMessageConsumer {

    private final TransactionMapper transactionMapper;
    private final BudgetService budgetService;

    @KafkaListener(topics = "oneTimeTransaction-update", groupId = "budget")
    public void listenFixedMonthlyTransactionChange(ConsumerRecord<String, JsonNode> cr, JsonNode payload) {
        JsonNode jsonOneTimeTransactionDto = payload.get("oneTimeTransaction");
        JsonNode jsonUpdateType = payload.get("updateType");

        final var objectMapper = new ObjectMapper();

        OneTimeTransactionDto oneTimeTransactionDto = null;
        UpdateType updateType = null;

        try{
            oneTimeTransactionDto = objectMapper.treeToValue(jsonOneTimeTransactionDto, OneTimeTransactionDto.class);
            updateType = objectMapper.treeToValue(jsonUpdateType, UpdateType.class);
        }catch (Exception e){
            System.out.println("Json Parsing error\n"+e);
        }

        budgetService.variableMonthlyTransactionChange(transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto), updateType);
    }

    @KafkaListener(topics = "recurringTransaction-update", groupId = "budget")
    public void listenVariableMonthlyTransactionChange(ConsumerRecord<String, JsonNode> cr, JsonNode payload) {
        JsonNode jsonOneTimeTransactionDto = payload.get("recurringTransaction");
        JsonNode jsonUpdateType = payload.get("updateType");

        final var objectMapper = new ObjectMapper();

        RecurringTransactionDto recurringTransactionDto = null;
        UpdateType updateType = null;

        try{
            recurringTransactionDto = objectMapper.treeToValue(jsonOneTimeTransactionDto, RecurringTransactionDto.class);
            updateType = objectMapper.treeToValue(jsonUpdateType, UpdateType.class);
        }catch (Exception e){
            System.out.println("Json Parsing error\n"+e);
        }

        budgetService.fixedTransactionChange(transactionMapper.recurringMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto), updateType);
    }
}
