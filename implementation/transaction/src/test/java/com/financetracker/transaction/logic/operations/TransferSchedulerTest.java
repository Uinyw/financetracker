package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.logic.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferSchedulerTest extends IntegrationTestBase {

    @MockBean
    private TransferService transferService;

    private TransferScheduler transferScheduler;

    @BeforeEach
    void setUp() {
        transferScheduler = new TransferScheduler(Clock.systemUTC(), oneTimeTransactionService, recurringTransactionService);
    }

    @Test
    void givenOneTimeTransactionsDueYesterdayTodayAndTomorrow_whenTransferOnDueDate_thenTransactionsDueYesterdayAndTodayAreTransferred() {
        doNothing().when(transferService).transfer(any(), any());

        final var oneTimeTransactionDueYesterday = OneTimeTransaction.with()
                .id(UUID.randomUUID().toString())
                .name("OneTime Payment")
                .date(LocalDate.now().minusDays(1))
                .type(Type.EXPENSE)
                .amount(new MonetaryAmount(new BigDecimal("14.99")))
                .transfer(new Transfer(UUID.randomUUID().toString(), null, null, "Thomas BankAccount"))
                .labels(Collections.emptySet())
                .build();

        final var oneTimeTransactionDueToday = OneTimeTransaction.with()
                .id(UUID.randomUUID().toString())
                .name("OneTime Payment")
                .date(LocalDate.now())
                .type(Type.EXPENSE)
                .amount(new MonetaryAmount(new BigDecimal("14.99")))
                .transfer(new Transfer(UUID.randomUUID().toString(), null, null, "Thomas BankAccount"))
                .labels(Collections.emptySet())
                .build();

        final var oneTimeTransactionDueTomorrow = OneTimeTransaction.with()
                .id(UUID.randomUUID().toString())
                .name("OneTime Payment")
                .date(LocalDate.now().plusDays(1))
                .type(Type.EXPENSE)
                .amount(new MonetaryAmount(new BigDecimal("14.99")))
                .transfer(new Transfer(UUID.randomUUID().toString(), null, null, "Thomas BankAccount"))
                .labels(Collections.emptySet())
                .build();

        oneTimeTransactionDueYesterday.setTransferStatus(TransferStatus.INITIAL);
        oneTimeTransactionDueToday.setTransferStatus(TransferStatus.INITIAL);
        oneTimeTransactionDueTomorrow.setTransferStatus(TransferStatus.INITIAL);
        oneTimeTransactionRepository.saveAll(List.of(oneTimeTransactionDueYesterday, oneTimeTransactionDueToday, oneTimeTransactionDueTomorrow));

        transferScheduler.transferOneTimeTransactionsOnDueDate();

        verify(transferService, times(2)).transfer(any(), any());

        final var oneTimeTransactionDueYesterdayFromDB = oneTimeTransactionRepository.findById(oneTimeTransactionDueYesterday.getId()).orElseGet(Assertions::fail);
        final var oneTimeTransactionDueTodayFromDB = oneTimeTransactionRepository.findById(oneTimeTransactionDueToday.getId()).orElseGet(Assertions::fail);
        final var oneTimeTransactionDueTomorrowFromDB = oneTimeTransactionRepository.findById(oneTimeTransactionDueTomorrow.getId()).orElseGet(Assertions::fail);

        assertThat(oneTimeTransactionDueYesterdayFromDB.getTransferStatus(), is(TransferStatus.SUCCESSFUL));
        assertThat(oneTimeTransactionDueTodayFromDB.getTransferStatus(), is(TransferStatus.SUCCESSFUL));
        assertThat(oneTimeTransactionDueTomorrowFromDB.getTransferStatus(), is(TransferStatus.INITIAL));
    }

    @Test
    void givenRecurringTransactionWithFixedAmountAndStartDateLastMonth_whenTransferOnDueDate_thenTwoTransactionRecordsAreCreatedAndTransferredTransferred() {
        doNothing().when(transferService).transfer(any(), any());

        final var recurringTransactionWithStartDateLastMonth = RecurringTransaction.with()
                .id(UUID.randomUUID().toString())
                .name("OneTime Payment")
                .startDate(LocalDate.now().minusMonths(1))
                .type(Type.EXPENSE)
                .periodicity(Periodicity.MONTHLY)
                .fixedAmount(new MonetaryAmount(new BigDecimal("200")))
                .transfer(new Transfer(UUID.randomUUID().toString(), null, null, "Thomas BankAccount"))
                .labels(Collections.emptySet())
                .build();

        recurringTransactionRepository.save(recurringTransactionWithStartDateLastMonth);

        transferScheduler.transferRecurringTransactionsOnDueDate();

        verify(transferService, times(2)).transfer(any(), any());

        final var recurringTransactionFromDB = recurringTransactionRepository.findById(recurringTransactionWithStartDateLastMonth.getId()).orElseGet(Assertions::fail);

        assertThat(recurringTransactionFromDB.getTransactionRecords().size(), is(2));
        assertThat(recurringTransactionFromDB.getTransactionRecords(), everyItem(hasProperty("transferStatus", is(TransferStatus.SUCCESSFUL))));
        assertThat(recurringTransactionFromDB.getTransactionRecords(), hasItem(hasProperty("date", is(recurringTransactionFromDB.getStartDate()))));
        assertThat(recurringTransactionFromDB.getTransactionRecords(), hasItem(hasProperty("date", is(recurringTransactionFromDB.getStartDate().plusMonths(1)))));
    }

    @Test
    void givenRecurringTransactionWithoutFixedAmountAndStartDateLastMonth_whenTransferOnDueDate_thenNoTransactionRecordsAreCreated() {
        doNothing().when(transferService).transfer(any(), any());

        final var recurringTransactionWithStartDateLastMonth = RecurringTransaction.with()
                .id(UUID.randomUUID().toString())
                .name("OneTime Payment")
                .startDate(LocalDate.now().minusMonths(1))
                .type(Type.EXPENSE)
                .periodicity(Periodicity.MONTHLY)
                .transfer(new Transfer(UUID.randomUUID().toString(), null, null, "Thomas BankAccount"))
                .labels(Collections.emptySet())
                .build();

        recurringTransactionRepository.save(recurringTransactionWithStartDateLastMonth);

        transferScheduler.transferRecurringTransactionsOnDueDate();

        verify(transferService, times(0)).transfer(any(), any());

        final var recurringTransactionFromDB = recurringTransactionRepository.findById(recurringTransactionWithStartDateLastMonth.getId()).orElseGet(Assertions::fail);

        assertThat(recurringTransactionFromDB.getTransactionRecords().size(), is(0));
    }
}
