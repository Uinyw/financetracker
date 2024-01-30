package com.financetracker.savingsgoal.TestSetup;

import com.financetracker.savingsgoal.client.BankAccountProvider;
import com.financetracker.savingsgoal.client.TransactionProvider;
import com.financetracker.savingsgoal.kafka.MessageConsumer;
import com.financetracker.savingsgoal.model.PeriodicalSavingsGoalRepository;
import com.financetracker.savingsgoal.model.RuleBasedSavingsGoalMapper;
import com.financetracker.savingsgoal.model.RuleBasedSavingsGoalRepository;
import com.financetracker.savingsgoal.model.SavingsGoalMapper;
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

    protected static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

    @Autowired
    private RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository;
    @Autowired
    public PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;

    @Autowired
    public SavingsGoalMapper savingsGoalMapper;
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
