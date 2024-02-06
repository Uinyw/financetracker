package com.financetracker.savingsgoal.TestSetup;

import com.financetracker.savingsgoal.infrastructure.client.bankaccount.BankAccountProvider;
import com.financetracker.savingsgoal.infrastructure.client.transaction.TransactionProvider;
import com.financetracker.savingsgoal.infrastructure.kafka.MessageConsumer;
import com.financetracker.savingsgoal.infrastructure.db.PeriodicalSavingsGoalRepository;
import com.financetracker.savingsgoal.api.mapping.RuleBasedSavingsGoalMapper;
import com.financetracker.savingsgoal.infrastructure.db.RuleBasedSavingsGoalRepository;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class IntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    private RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository;

    @Autowired
    public PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;

    @Autowired
    public RuleBasedSavingsGoalMapper ruleBasedSavingsGoalMapper;

    @SpyBean
    public BankAccountProvider bankAccountProvider;

    @SpyBean
    public TransactionProvider transactionProvider;

    @MockBean
    public MessageConsumer messageConsumer;

    @AfterEach
    void tearDown() {
        Mockito.reset(bankAccountProvider);
        Mockito.reset(transactionProvider);
        periodicalSavingsGoalRepository.deleteAll();
        ruleBasedSavingsGoalRepository.deleteAll();
    }
}
