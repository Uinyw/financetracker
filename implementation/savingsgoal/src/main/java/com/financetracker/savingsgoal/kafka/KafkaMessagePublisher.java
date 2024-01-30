package com.financetracker.savingsgoal.kafka;

import org.openapitools.client.model.OneTimeTransactionDto;

public interface KafkaMessagePublisher {
    Boolean sendScheduledMessage(OneTimeTransactionDto transaction) throws Exception;

    String testReceiveTopic1() throws Exception;


}
