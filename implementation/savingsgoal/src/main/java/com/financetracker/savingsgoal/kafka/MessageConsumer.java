package com.financetracker.savingsgoal.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.BankAccountDto;

public interface MessageConsumer {
    void listenBankAccountChange(ConsumerRecord<String, BankAccountDto> cr, BankAccountDto payload);

}
