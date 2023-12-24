package com.financetracker.transaction;

import com.financetracker.transaction.api.mapping.OneTimeTransactionMapper;
import com.financetracker.transaction.api.mapping.RecurringTransactionMapper;
import com.financetracker.transaction.infrastructure.db.TransactionRepository;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    protected static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

    @Autowired
    private TransactionRepository<OneTimeTransaction> oneTimeTransactionTransactionRepository;

    @Autowired
    public OneTimeTransactionMapper oneTimeTransactionMapper;
    @Autowired
    public RecurringTransactionMapper recurringTransactionMapper;

    @AfterEach
    void tearDown() {
        oneTimeTransactionTransactionRepository.deleteAll();
    }
}
