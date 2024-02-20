package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.logic.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceTest extends IntegrationTestBase {

    private BankAccountDto bankAccount1;
    private BankAccountDto bankAccount2;
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        bankAccount1 = BankAccountDto.builder()
                .id(UUID.randomUUID())
                .balance(MonetaryAmountDto.builder().amount(100.0).build())
                .build();

        bankAccount2 = BankAccountDto.builder()
                .id(UUID.randomUUID())
                .balance(MonetaryAmountDto.builder().amount(100.0).build())
                .build();

        transferService = new TransferService(new MockBankAccountProvider(List.of(bankAccount1, bankAccount2)));
    }

    @Test
    void givenInvalidTransferAttributes_whenCreateTransfer_thenNotParseableException() {
        assertThrows(NotParseableException.class, () -> new Transfer(null, null, null, null));
        assertThrows(NotParseableException.class, () -> new Transfer(null, null, UUID.randomUUID().toString(), null));
        assertThrows(NotParseableException.class, () -> new Transfer(UUID.randomUUID().toString(), "External Source", UUID.randomUUID().toString(), null));
        assertThrows(NotParseableException.class, () -> new Transfer(UUID.randomUUID().toString(), null, null, null));
        assertThrows(NotParseableException.class, () -> new Transfer(UUID.randomUUID().toString(), null, UUID.randomUUID().toString(), "External Target"));
    }

    @Test
    void givenIncomeWithTransferStatusInitial_whenTransfer_thenTransferSuccessful() {
        final var incomeTransfer = new Transfer(null, "Income Source", bankAccount1.getId().toString(), null);

        final var transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transferStatus(TransferStatus.INITIAL)
                .amount(new MonetaryAmount(BigDecimal.ONE))
                .build();

        transferService.transfer(incomeTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(101.0));
    }

    @Test
    void givenIncomeWithTransferStatusSuccessful_whenTransfer_thenNoTransfer() {
        final var incomeTransfer = new Transfer(null, "Income Source",  bankAccount1.getId().toString(), null);

        final var transactionRecord = TransactionRecord.with()
            .id(UUID.randomUUID().toString())
            .transferStatus(TransferStatus.SUCCESSFUL)
            .amount(new MonetaryAmount(BigDecimal.ONE))
            .build();

        transferService.transfer(incomeTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(100.0));
    }

    @Test
    void givenExpenseWithTransferStatusInitial_whenTransfer_thenTransferSuccessful() {
        final var expenseTransfer = new Transfer(bankAccount1.getId().toString(), null, null, "External Target");

        final var transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transferStatus(TransferStatus.INITIAL)
                .amount(new MonetaryAmount(BigDecimal.ONE))
                .build();

        transferService.transfer(expenseTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(99.0));
    }

    @Test
    void givenShiftWithTransferStatusInitial_whenTransfer_thenTransferSuccessful() {
        final var expenseTransfer = new Transfer(bankAccount1.getId().toString(), null, bankAccount2.getId().toString(), null);

        final var transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transferStatus(TransferStatus.INITIAL)
                .amount(new MonetaryAmount(BigDecimal.ONE))
                .build();

        transferService.transfer(expenseTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(99.0));
        assertThat(bankAccount2.getBalance().getAmount(), is(101.0));
    }

    @Test
    void givenIncomeWithTransferStatusSuccessful_whenRollbackTransfer_thenTransferRolledBackSuccessful() {
        final var incomeTransfer = new Transfer(null, "Income Source", bankAccount1.getId().toString(), null);

        final var transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transferStatus(TransferStatus.SUCCESSFUL)
                .amount(new MonetaryAmount(BigDecimal.ONE))
                .build();

        transferService.rollbackTransfer(incomeTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(99.0));
    }

    @Test
    void givenIncomeWithTransferStatusInitial_whenRollbackTransfer_thenNoTransferRollback() {
        final var incomeTransfer = new Transfer(null, "Income Source",  bankAccount1.getId().toString(), null);

        final var transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transferStatus(TransferStatus.INITIAL)
                .amount(new MonetaryAmount(BigDecimal.ONE))
                .build();

        transferService.rollbackTransfer(incomeTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(100.0));
    }

    @Test
    void givenExpenseWithTransferStatusSuccessful_whenRollbackTransfer_thenTransferRolledBackSuccessful() {
        final var expenseTransfer = new Transfer(bankAccount1.getId().toString(), null, null, "External Target");

        final var transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transferStatus(TransferStatus.SUCCESSFUL)
                .amount(new MonetaryAmount(BigDecimal.ONE))
                .build();

        transferService.rollbackTransfer(expenseTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(101.0));
    }

    @Test
    void givenShiftWithTransferStatusSuccessful_whenRollbackTransfer_thenTransferSuccessful() {
        final var expenseTransfer = new Transfer(bankAccount1.getId().toString(), null, bankAccount2.getId().toString(), null);

        final var transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transferStatus(TransferStatus.SUCCESSFUL)
                .amount(new MonetaryAmount(BigDecimal.ONE))
                .build();

        transferService.rollbackTransfer(expenseTransfer, transactionRecord);

        assertThat(bankAccount1.getBalance().getAmount(), is(101.0));
        assertThat(bankAccount2.getBalance().getAmount(), is(99.0));
    }


}
