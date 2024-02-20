package com.bank.account.logic.operations;

import com.bank.account.IntegrationTestBase;
import com.bank.account.logic.model.BankAccount;
import com.bank.account.logic.model.Label;
import com.bank.account.logic.model.MonetaryAmount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.BankAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankAccountServiceTest extends IntegrationTestBase {

    @Value("${tpd.topic-bank-account-update}")
    private String bankAccountUpdate;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaMessageListenerContainer<String, Object> container;
    private BlockingQueue<ConsumerRecord<String, Object>> consumerRecords;

    @BeforeEach
    void setup() {
        consumerRecords = new LinkedBlockingQueue<>();

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("test-group-id", "false", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, Object> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        ContainerProperties containerProperties = new ContainerProperties(bankAccountUpdate);
        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        container.setupMessageListener((MessageListener<String, Object>) record -> consumerRecords.add(record));
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @AfterEach
    void after() {
        container.stop();
    }

    @Test
    void givenBankAccount_whenGetBankAccounts_thenBankAccountIsReturned() {
        final var bankAccount = BankAccount.with()
                .id(UUID.randomUUID().toString())
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .build();
        bankAccountRepository.save(bankAccount);

        final var retrievedBankAccounts = bankAccountService.getBankAccounts();
        assertThat(retrievedBankAccounts.size(), is(1));
        assertThat(retrievedBankAccounts.get(0).getId(), is(bankAccount.getId()));
        assertThat(retrievedBankAccounts.get(0).getName(), is(bankAccount.getName()));
        assertThat(retrievedBankAccounts.get(0).getDescription(), is(bankAccount.getDescription()));
        assertThat(retrievedBankAccounts.get(0).getBalance().amount(), comparesEqualTo(bankAccount.getBalance().amount()));
        assertThat(retrievedBankAccounts.get(0).getDispositionLimit().amount(), comparesEqualTo(bankAccount.getDispositionLimit().amount()));
        assertThat(retrievedBankAccounts.get(0).getLabels(), is(bankAccount.getLabels()));
    }

    @Test
    void givenBankAccount_whenGetBankAccountById_thenBankAccountIsReturned() {
        final var bankAccount = BankAccount.with()
                .id(UUID.randomUUID().toString())
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .build();
        bankAccountRepository.save(bankAccount);

        final var bankAccountById = bankAccountService.getBankAccount(bankAccount.getId());
        assertTrue(bankAccountById.isPresent());
        assertThat(bankAccountById.get().getId(), is(bankAccount.getId()));
    }

    @Test
    void givenBankAccount_whenGetBankAccountByInvalidId_thenBankAccountIsNotReturned() {
        final var bankAccount = BankAccount.with()
                .id(UUID.randomUUID().toString())
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .build();
        bankAccountRepository.save(bankAccount);

        final var bankAccountById = bankAccountService.getBankAccount(UUID.randomUUID().toString());
        assertTrue(bankAccountById.isEmpty());
    }

    @Test
    void givenBankAccount_whenCreateBankAccount_thenBankAccountExistsAndKafkaMessageIsSent() throws InterruptedException, JsonProcessingException {
        final var bankAccount = BankAccount.with()
                .id(UUID.randomUUID().toString())
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .build();

        bankAccountService.createBankAccount(bankAccount);

        final var bankAccountFromDb = bankAccountRepository.findById(bankAccount.getId());
        assertTrue(bankAccountFromDb.isPresent());
        assertThat(bankAccountFromDb.get().getId(), is(bankAccount.getId()));
        assertThat(bankAccountFromDb.get().getName(), is(bankAccount.getName()));
        assertThat(bankAccountFromDb.get().getDescription(), is(bankAccount.getDescription()));
        assertThat(bankAccountFromDb.get().getBalance().amount(), comparesEqualTo(bankAccount.getBalance().amount()));
        assertThat(bankAccountFromDb.get().getDispositionLimit().amount(), comparesEqualTo(bankAccount.getDispositionLimit().amount()));
        assertThat(bankAccountFromDb.get().getLabels(), is(bankAccount.getLabels()));

        final var consumedRecord = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecord);
        final var consumedBankAccount = new ObjectMapper().readValue(consumedRecord.value().toString(), BankAccountDto.class);
        assertThat(consumedBankAccount.getId().toString(), is(bankAccount.getId()));
    }

    @Test
    void givenBankAccount_whenDeleteBankAccount_thenBankAccountDoesNotExistAnymore() {
        final var bankAccount = BankAccount.with()
                .id(UUID.randomUUID().toString())
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .build();

        bankAccountRepository.save(bankAccount);

        bankAccountService.deleteBankAccount(bankAccount.getId());

        final var bankAccountFromDb = bankAccountRepository.findById(bankAccount.getId());
        assertTrue(bankAccountFromDb.isEmpty());
    }

    @Test
    void givenBankAccount_whenDeleteBankAccountByInvalidId_thenBankAccountStillExists() {
        final var bankAccount = BankAccount.with()
                .id(UUID.randomUUID().toString())
                .name("Basic Account")
                .description("A basic account")
                .balance(MonetaryAmount.DEFAULT)
                .dispositionLimit(MonetaryAmount.DEFAULT)
                .labels(Set.of(new Label("Basic")))
                .build();

        bankAccountRepository.save(bankAccount);

        bankAccountService.deleteBankAccount(UUID.randomUUID().toString());

        final var bankAccountFromDb = bankAccountRepository.findById(bankAccount.getId());
        assertTrue(bankAccountFromDb.isPresent());
        assertThat(bankAccountFromDb.get().getId(), is(bankAccount.getId()));
    }
}
