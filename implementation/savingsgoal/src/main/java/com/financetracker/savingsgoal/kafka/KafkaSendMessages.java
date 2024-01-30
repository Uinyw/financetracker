package com.financetracker.savingsgoal.kafka;

import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.TransferStatusDto;
import org.openapitools.client.model.TypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
public class KafkaSendMessages implements KafkaMessagePublisher{

    private static final Logger logger =
            LoggerFactory.getLogger(KafkaReceiveMessages.class);

    private final KafkaTemplate<String, Object> template;
    private final String topic1Name;
    private final String topic2Name;
    private final String topic3Name;

    //If new topic is defined, it also as to be set here!!
    public KafkaSendMessages(
            final KafkaTemplate<String, Object> template,
            @Value("${tpd.topic1-name}") final String topic1Name,
            @Value("${tpd.topic2-name}") final String topic2Name,
            @Value("${tpd.topic3-name}") final String topic3Name) {
        this.template = template;
        this.topic1Name = topic1Name;
        this.topic2Name = topic2Name;
        this.topic3Name = topic3Name;
    }

    @GetMapping("/scheduledMessage")
    public Boolean sendScheduledMessage(OneTimeTransactionDto transaction) throws Exception{
        this.template.send(topic2Name, transaction);
        logger.info("Message put in queue");
        return true;
    }

    //TODO to be deleted
    @GetMapping("/transactionTest")
    public String testReceiveTopic1() throws Exception {
        this.template.send(topic1Name, createRandomOneTimeTransactionDto());
        logger.info("All messages received");
        return "new Transaction!";
    }

    //TODO to be deleted
    private OneTimeTransactionDto createRandomOneTimeTransactionDto(){
        MonetaryAmountDto monetaryAmountDto = new MonetaryAmountDto();
        monetaryAmountDto.setAmount((double)Math.round(Math.random()*1000));

        final var oneTimeTransactionDto = OneTimeTransactionDto.builder()
                .amount(monetaryAmountDto)
                .type(TypeDto.INCOME)
                .name("random Name")
                .date(LocalDate.now().toString())
                .id(UUID.randomUUID())
                .description("random Description")
                .transferStatus(TransferStatusDto.INITIAL).build();

        return oneTimeTransactionDto;
    }
}
