package com.example.Analytics.logic.api.mapping;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.logic.model.budgetModel.FixedTransaction;
import com.example.Analytics.logic.model.budgetModel.Transaction;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AnalyticsTransactionMapperTest extends IntegrationTestBase {
    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    void givenOneTimeTransactionDto_whenMap_thenVariableMonthlyTransactionsExists(){
        final UUID id = UUID.randomUUID();
        MonetaryAmountDto amountDto =  new MonetaryAmountDto(10.0);
        TransferDto transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        OneTimeTransactionDto oneTimeTransactionDto = createOneTimeTransactionDto(id, TypeDto.EXPENSE, List.of("label 1"), transfer, amountDto, LocalDate.now().toString(), TransferStatusDto.SUCCESSFUL);

        List<VariableMonthlyTransaction> transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto);
        VariableMonthlyTransaction transaction = transactionList.get(0);
        Transaction referenceTransaction = transaction.getReferenceTransactions().get(0);

        assertThat(oneTimeTransactionDto.getId(), is(referenceTransaction.getReferenceId()));
        assertThat(oneTimeTransactionDto.getTransfer().getTargetBankAccountId().toString(), is(referenceTransaction.getBankAccountTarget().toString()));
        assertThat(oneTimeTransactionDto.getTransfer().getSourceBankAccountId().toString(), is(referenceTransaction.getBankAccountSource().toString()));
        assertThat(oneTimeTransactionDto.getAmount().getAmount(), is(referenceTransaction.getAmount().getAmount()));
        assertThat(oneTimeTransactionDto.getType().getValue(), is(referenceTransaction.getType().name()));
        assertThat(oneTimeTransactionDto.getLabels().get(0), is(transaction.getName()));
        assertThat(oneTimeTransactionDto.getLabels().get(0), is(transaction.getCategory().getName()));
        assertThat(oneTimeTransactionDto.getDate(), is(referenceTransaction.getDate().toString()));

        amountDto =  null;
        transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        oneTimeTransactionDto = createOneTimeTransactionDto(id, null, List.of("label 1"), transfer, amountDto, null, TransferStatusDto.SUCCESSFUL);

        transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto);
        transaction = transactionList.get(0);
        referenceTransaction = transaction.getReferenceTransactions().get(0);

        assertThat(oneTimeTransactionDto.getId(), is(referenceTransaction.getReferenceId()));
        assertThat(oneTimeTransactionDto.getTransfer().getTargetBankAccountId().toString(), is(referenceTransaction.getBankAccountTarget().toString()));
        assertThat(oneTimeTransactionDto.getTransfer().getSourceBankAccountId().toString(), is(referenceTransaction.getBankAccountSource().toString()));
        assertThat(referenceTransaction.getAmount().getAmount(), is(0.0));
        assertThat(oneTimeTransactionDto.getType(), is(referenceTransaction.getType()));
        assertThat(oneTimeTransactionDto.getLabels().get(0), is(transaction.getName()));
        assertThat(oneTimeTransactionDto.getLabels() == null, is(transaction.getCategory() == null));
        assertThat(oneTimeTransactionDto.getDate() == null, is(referenceTransaction.getDate() == null));
    }

    @Test
    void givenOneTimeTransactionDto_whenMapWithShiftNull_thenVariableMonthlyTransactionsExists(){
        final UUID id = UUID.randomUUID();

        MonetaryAmountDto amountDto =  null;
        TransferDto transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        OneTimeTransactionDto oneTimeTransactionDto = createOneTimeTransactionDto(id, TypeDto.SHIFT, List.of("label 1"), transfer, amountDto, null, TransferStatusDto.SUCCESSFUL);

        List<VariableMonthlyTransaction> transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto);
        VariableMonthlyTransaction transaction = transactionList.get(0);
        Transaction referenceTransaction = transaction.getReferenceTransactions().get(0);

        assertThat(oneTimeTransactionDto.getId(), is(referenceTransaction.getReferenceId()));
        assertThat(oneTimeTransactionDto.getTransfer().getTargetBankAccountId().toString(), is(referenceTransaction.getBankAccountTarget().toString()));
        assertThat(oneTimeTransactionDto.getTransfer().getSourceBankAccountId().toString(), is(referenceTransaction.getBankAccountSource().toString()));
        assertThat(referenceTransaction.getAmount().getAmount(), is(0.0));
        assertThat(null, is(referenceTransaction.getType()));
        assertThat(oneTimeTransactionDto.getLabels().get(0), is(transaction.getName()));
        assertThat(oneTimeTransactionDto.getLabels() == null, is(transaction.getCategory() == null));
        assertThat(oneTimeTransactionDto.getDate() == null, is(referenceTransaction.getDate() == null));
    }

    @Test
    void givenOneTimeTransactionDto_whenMapWithLabelsNull_thenVariableMonthlyTransactionsExists(){
        final UUID id = UUID.randomUUID();
        MonetaryAmountDto amountDto =  new MonetaryAmountDto(1.0);
        TransferDto transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());;
        OneTimeTransactionDto oneTimeTransactionDto = createOneTimeTransactionDto(id, TypeDto.SHIFT, null, transfer, amountDto, null, TransferStatusDto.SUCCESSFUL);

        List<VariableMonthlyTransaction> transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto);
        assertThat(transactionList.size(), is(0));
    }

    @Test
    void givenOneTimeTransactionDto_whenMapWithTransferNull_thenVariableMonthlyTransactionsExists(){
        final UUID id = UUID.randomUUID();
        MonetaryAmountDto amountDto =  new MonetaryAmountDto(1.0);
        TransferDto transfer = null;
        OneTimeTransactionDto oneTimeTransactionDto = createOneTimeTransactionDto(id, TypeDto.SHIFT, null, transfer, amountDto, null, TransferStatusDto.SUCCESSFUL);

        List<VariableMonthlyTransaction> transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto);
        assertThat(transactionList.size(), is(0));
    }

    @Test
    void givenRecurringTransactionDto_whenMap_thenFixedTransactionsExists(){
        UUID id = UUID.randomUUID();
        MonetaryAmountDto amountDto = new MonetaryAmountDto(10.0);
        TransactionRecordDto transactionRecord = createTransactionRecordDto(amountDto, LocalDate.now().toString(), TransferStatusDto.INITIAL, id);
        TransferDto transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        for (PeriodicityDto periodicity : List.of(PeriodicityDto.MONTHLY, PeriodicityDto.YEARLY, PeriodicityDto.HALF_YEARLY, PeriodicityDto.QUARTERLY)) {

            RecurringTransactionDto recurringTransactionDto = createRecurringTransactionDto(id, TypeDto.INCOME, List.of("label 1"), transfer, periodicity, LocalDate.now().toString(), amountDto, List.of(transactionRecord));

            List<FixedTransaction> fixedTransactionList = transactionMapper.recurringMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto);
            FixedTransaction transaction = fixedTransactionList.get(0);
            Transaction referenceTransaction = transaction.getReferenceTransactions().get(0);

            assertThat(referenceTransaction.getReferenceId(), is(recurringTransactionDto.getId()));
            assertThat(transaction.getCategory().getName(), is(recurringTransactionDto.getLabels().get(0)));
            assertThat(transaction.getName(), is(recurringTransactionDto.getLabels().get(0)));
            assertThat(transaction.getPeriodicity().name(), is(recurringTransactionDto.getPeriodicity().getValue()));

            assertThat(referenceTransaction.getBankAccountSource().toString(), is(transfer.getSourceBankAccountId().toString()));
            assertThat(referenceTransaction.getBankAccountTarget().toString(), is(transfer.getTargetBankAccountId().toString()));
            assertThat(referenceTransaction.getDate().toString(), is(recurringTransactionDto.getStartDate().toString()));
            assertThat(referenceTransaction.getAmount().getAmount(), is(recurringTransactionDto.getFixedAmount().getAmount()));
            assertThat(referenceTransaction.getType().name(), is(recurringTransactionDto.getType().getValue()));
        }
    }

    @Test
    void givenRecurringTransactionDto_whenMapWithDateNull_thenFixedTransactionsExists(){
        UUID id = UUID.randomUUID();
        MonetaryAmountDto amountDto = new MonetaryAmountDto(10.0);
        TransactionRecordDto transactionRecord = createTransactionRecordDto(amountDto, null, TransferStatusDto.INITIAL, id);
        TransferDto transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        RecurringTransactionDto recurringTransactionDto = createRecurringTransactionDto(id, TypeDto.INCOME, List.of("label 1"), transfer, PeriodicityDto.MONTHLY, LocalDate.now().toString(), amountDto, List.of(transactionRecord));

        List<FixedTransaction> fixedTransactionList = transactionMapper.recurringMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto);
        FixedTransaction transaction = fixedTransactionList.get(0);
        Transaction referenceTransaction = transaction.getReferenceTransactions().get(0);

        assertThat(referenceTransaction.getReferenceId(), is(recurringTransactionDto.getId()));
        assertThat(transaction.getCategory().getName(), is(recurringTransactionDto.getLabels().get(0)));
        assertThat(transaction.getName(), is(recurringTransactionDto.getLabels().get(0)));
        assertThat(transaction.getPeriodicity().name(), is(recurringTransactionDto.getPeriodicity().getValue()));
        assertThat(referenceTransaction.getBankAccountSource().toString(), is(transfer.getSourceBankAccountId().toString()));
        assertThat(referenceTransaction.getBankAccountTarget().toString(), is(transfer.getTargetBankAccountId().toString()));
        assertThat(referenceTransaction.getDate().toString(), is(recurringTransactionDto.getStartDate().toString()));
        assertThat(referenceTransaction.getAmount().getAmount(), is(recurringTransactionDto.getFixedAmount().getAmount()));
        assertThat(referenceTransaction.getType().name(), is(recurringTransactionDto.getType().getValue()));
    }

    @Test
    void givenRecurringTransactionDto_whenMapWithTypeNull_thenFixedTransactionsExists(){
        UUID id = UUID.randomUUID();
        MonetaryAmountDto amountDto = new MonetaryAmountDto(10.0);
        TransactionRecordDto transactionRecord = createTransactionRecordDto(amountDto, LocalDate.now().toString(), TransferStatusDto.INITIAL, id);
        TransferDto transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        RecurringTransactionDto recurringTransactionDto = createRecurringTransactionDto(id, null, List.of("label 1"), transfer, PeriodicityDto.MONTHLY, LocalDate.now().toString(), amountDto, List.of(transactionRecord));

        List<FixedTransaction> fixedTransactionList = transactionMapper.recurringMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto);
        FixedTransaction transaction = fixedTransactionList.get(0);
        Transaction referenceTransaction = transaction.getReferenceTransactions().get(0);

        assertThat(referenceTransaction.getReferenceId(), is(recurringTransactionDto.getId()));
        assertThat(transaction.getCategory().getName(), is(recurringTransactionDto.getLabels().get(0)));
        assertThat(transaction.getName(), is(recurringTransactionDto.getLabels().get(0)));
        assertThat(transaction.getPeriodicity().name(), is(recurringTransactionDto.getPeriodicity().getValue()));
        assertThat(referenceTransaction.getBankAccountSource().toString(), is(transfer.getSourceBankAccountId().toString()));
        assertThat(referenceTransaction.getBankAccountTarget().toString(), is(transfer.getTargetBankAccountId().toString()));
        assertThat(referenceTransaction.getDate().toString(), is(recurringTransactionDto.getStartDate().toString()));
        assertThat(referenceTransaction.getAmount().getAmount(), is(recurringTransactionDto.getFixedAmount().getAmount()));
        assertThat(referenceTransaction.getType(), is(recurringTransactionDto.getType()));
    }

    @Test
    void givenRecurringTransactionDto_whenMapWithFixedAmountNull_thenFixedTransactionsExists(){
        UUID id = UUID.randomUUID();
        MonetaryAmountDto amountDto = null;
        TransactionRecordDto transactionRecord = createTransactionRecordDto(amountDto, LocalDate.now().toString(), TransferStatusDto.INITIAL, id);
        TransferDto transfer = createTransferDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        RecurringTransactionDto recurringTransactionDto = createRecurringTransactionDto(id, TypeDto.INCOME, List.of("label 1"), transfer, PeriodicityDto.MONTHLY, LocalDate.now().toString(), amountDto, List.of(transactionRecord));

        List<FixedTransaction> fixedTransactionList = transactionMapper.recurringMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto);
        FixedTransaction transaction = fixedTransactionList.get(0);
        Transaction referenceTransaction = transaction.getReferenceTransactions().get(0);

        assertThat(referenceTransaction.getReferenceId(), is(recurringTransactionDto.getId()));
        assertThat(transaction.getCategory().getName(), is(recurringTransactionDto.getLabels().get(0)));
        assertThat(transaction.getName(), is(recurringTransactionDto.getLabels().get(0)));
        assertThat(transaction.getPeriodicity().name(), is(recurringTransactionDto.getPeriodicity().getValue()));
        assertThat(referenceTransaction.getBankAccountSource().toString(), is(transfer.getSourceBankAccountId().toString()));
        assertThat(referenceTransaction.getBankAccountTarget().toString(), is(transfer.getTargetBankAccountId().toString()));
        assertThat(referenceTransaction.getDate().toString(), is(recurringTransactionDto.getStartDate().toString()));
        assertThat(null, is(recurringTransactionDto.getFixedAmount()));
        assertThat(referenceTransaction.getType().name(), is(recurringTransactionDto.getType().getValue()));
    }

    private TransactionRecordDto createTransactionRecordDto(MonetaryAmountDto monetaryAmount, String date, TransferStatusDto transferStatus, UUID id){
        return TransactionRecordDto.builder()
                .amount(monetaryAmount)
                .transferStatus(transferStatus)
                .id(id)
                .date(date)
                .build();
    }
    private RecurringTransactionDto createRecurringTransactionDto(UUID id, TypeDto type, List<String> labels, TransferDto transfer, PeriodicityDto periodicity, String startDate, MonetaryAmountDto amountDto, List<TransactionRecordDto> transactionRecords){
        return RecurringTransactionDto.builder()
                .id(id)
                .name("Recurring transaction")
                .description("Recurring transaction description")
                .transactionRecords(transactionRecords)
                .type(type)
                .labels(labels)
                .transfer(transfer)
                .periodicity(periodicity)
                .startDate(startDate)
                .fixedAmount(amountDto)
                .build();
    }
    private OneTimeTransactionDto createOneTimeTransactionDto(UUID uuid, TypeDto type, List<String> labels, TransferDto transfer, MonetaryAmountDto amountDto, String date, TransferStatusDto transferStatus){
        return OneTimeTransactionDto.builder()
                .id(uuid)
                .name("Transaction Name")
                .description("Transaction Description")
                .type(type)
                .labels(labels)
                .transfer(transfer)
                .amount(amountDto)
                .transferStatus(transferStatus)
                .date(date)
                .build();
    }
    private TransferDto createTransferDto(UUID externalSourceId, UUID targetBankAccountId, UUID sourceBankAccountId, UUID externalTargetId){
        return TransferDto.builder()
                .externalSourceId(externalSourceId.toString())
                .externalTargetId(externalTargetId.toString())
                .targetBankAccountId(targetBankAccountId)
                .sourceBankAccountId(sourceBankAccountId)
                .build();
    }

}
