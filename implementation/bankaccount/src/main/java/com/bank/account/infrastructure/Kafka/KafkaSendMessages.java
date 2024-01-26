package com.bank.account.infrastructure.Kafka;

import com.bank.account.logic.operations.BankAccountService;
import org.openapitools.model.BankAccountDto;
import org.openapitools.model.MonetaryAmountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class KafkaSendMessages {

        private static final Logger logger =
                LoggerFactory.getLogger(com.bank.account.infrastructure.Kafka.KafkaRessource.class);

        private final KafkaTemplate<String, Object> template;
        private final String topic2Name;

        //If new topic is defined, it also as to be set here!!
        public KafkaSendMessages(
                final KafkaTemplate<String, Object> template,
                @Value("${tpd.topic1-name}") final String topic1Name,
                @Value("${tpd.topic2-name}") final String topic2Name) {
            this.template = template;
            this.topic2Name = topic2Name;
        }

    //TODO send message after change of bank account
    @GetMapping("/balanceChange")
    public Boolean balanceChange(BankAccountDto bankAccountDto) throws Exception{
            //TODO here a random bank account gets build
        this.template.send(topic2Name, BankAccountDto.builder().id(UUID.randomUUID()).balance(MonetaryAmountDto.builder().amount(Math.random()*1000).build()).build());
        logger.info("Message put in queue");
        return true;
    }
}
