package com.financetracker.savingsgoal.infrastructure.kafka;

import com.financetracker.savingsgoal.logic.operations.SavingsGoalService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaMessageConsumer implements MessageConsumer {

    private final SavingsGoalService savingsGoalService;

    @KafkaListener(topics = "bankaccount-update", groupId = "topic2")
    public void listenBankAccountChange(ConsumerRecord<String, BankAccountDto> cr, @Payload BankAccountDto payload) {
        savingsGoalService.onReceiveBankAccountChange(payload);
    }

}


