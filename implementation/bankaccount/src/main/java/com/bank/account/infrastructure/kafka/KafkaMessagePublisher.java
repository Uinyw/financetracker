package com.bank.account.infrastructure.kafka;

import com.bank.account.api.mapping.BankAccountMapper;
import com.bank.account.logic.model.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaMessagePublisher implements MessagePublisher {

    private final KafkaTemplate<String, Object> template;
    private final BankAccountMapper bankAccountMapper;

    @Value("${tpd.topic-bank-account-update}")
    private String bankAccountUpdate;

    public void publishMessageBankAccountUpdate(final BankAccount bankAccount) {
        this.template.send(bankAccountUpdate, bankAccountMapper.mapBankAccountModelToDto(bankAccount));
    }

}
