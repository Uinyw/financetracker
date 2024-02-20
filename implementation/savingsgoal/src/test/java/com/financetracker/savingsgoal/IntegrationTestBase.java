package com.financetracker.savingsgoal;

import com.financetracker.savingsgoal.infrastructure.client.bankaccount.BankAccountProvider;
import com.financetracker.savingsgoal.infrastructure.client.transaction.TransactionProvider;
import com.financetracker.savingsgoal.infrastructure.db.PeriodicalSavingsGoalRepository;
import com.financetracker.savingsgoal.infrastructure.db.RuleBasedSavingsGoalRepository;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(topics = "bankaccount-update")
public class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    protected static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

    @Autowired
    private RuleBasedSavingsGoalRepository ruleBasedSavingsGoalRepository;

    @Autowired
    public PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;

    @SpyBean
    public BankAccountProvider bankAccountProvider;

    @SpyBean
    public TransactionProvider transactionProvider;

    @AfterEach
    void tearDown() {
        Mockito.reset(bankAccountProvider);
        Mockito.reset(transactionProvider);
        periodicalSavingsGoalRepository.deleteAll();
        ruleBasedSavingsGoalRepository.deleteAll();
    }
}
