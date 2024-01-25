package com.financetracker.savingsgoal.kafka;

import com.financetracker.savingsgoal.Logic.SavingsGoalService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.openapitools.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
public class KafkaReceiveMessages {

    private static final Logger logger =
            LoggerFactory.getLogger(KafkaReceiveMessages.class);

    private final SavingsGoalService savingsGoalService;


    public KafkaReceiveMessages(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    @KafkaListener(topics = "transaction-queue", clientIdPrefix = "json",
            containerFactory = "OneTimeTransactionDtoKafkaListenerContainerFactory", groupId = "topic1")
    public void listenAsObject(ConsumerRecord<String, OneTimeTransactionDto> cr,
                               @Payload OneTimeTransactionDto payload) {
        logger.info("transaction-queue message received\nLogger 1 [JSON] received key {}: Type [{}] | Payload: {}", cr.key(),
                typeIdHeader(cr.headers()), payload);

    }

    @KafkaListener(topics = "account-update", clientIdPrefix = "json",
            containerFactory = "bankAccountDtoKafkaListenerContainerFactory", groupId = "topic2")
    public void listenBankAccountChange(ConsumerRecord<String, BankAccountDto> cr,
                               @Payload BankAccountDto payload) {
        logger.info("account-update listener success\nLogger 1 [JSON] received key {}: Type [{}] | Payload: {}", cr.key(),
                typeIdHeader(cr.headers()), payload);
        savingsGoalService.receivedNewTransaction(payload);

    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
