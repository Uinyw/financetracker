package com.financetracker.transaction.api;
import com.financetracker.transaction.api.mapping.OneTimeTransactionMapper;
import com.financetracker.transaction.api.mapping.RecurringTransactionMapper;
import com.financetracker.transaction.logic.operations.OneTimeTransactionService;
import com.financetracker.transaction.logic.operations.RecurringTransactionService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TransactionsApi;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.RecurringTransactionDto;
import org.openapitools.model.TransactionRecordDto;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TransactionResource implements TransactionsApi {

    private final OneTimeTransactionService oneTimeTransactionService;
    private final OneTimeTransactionMapper oneTimeTransactionMapper;

    private final RecurringTransactionService recurringTransactionService;
    private final RecurringTransactionMapper recurringTransactionMapper;

    @Override
    public List<OneTimeTransactionDto> transactionsOnetimeGet(UUID sourceBankAccount, UUID targetBankAccount) {
        return oneTimeTransactionService.getOneTimeTransactions().stream()
                .filter(transaction -> sourceBankAccount == null || (transaction.getTransfer().hasInternalSource() && transaction.getTransfer().getSourceBankAccountId().equals(sourceBankAccount.toString())))
                .filter(transaction -> targetBankAccount == null || (transaction.getTransfer().hasInternalTarget() && transaction.getTransfer().getTargetBankAccountId().equals(targetBankAccount.toString())))
                .map(oneTimeTransactionMapper::mapOneTimeTransactionModelToDto)
                .toList();
    }

    @Override
    public void transactionsOnetimePost(final OneTimeTransactionDto oneTimeTransactionDto) {
        oneTimeTransactionService.createOneTimeTransaction(oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Override
    public void transactionsOnetimeIdDelete(final String id) {
        oneTimeTransactionService.deleteOneTimeTransaction(id);
    }

    @Override
    public OneTimeTransactionDto transactionsOnetimeIdGet(final String id) {
        final var oneTimeTransaction = oneTimeTransactionService.getOneTimeTransaction(id).orElseThrow(NotFoundException::new);
        return oneTimeTransactionMapper.mapOneTimeTransactionModelToDto(oneTimeTransaction);
    }

    @Override
    public void transactionsOnetimeIdPatch(String id, OneTimeTransactionDto oneTimeTransactionDto) {
        oneTimeTransactionService.updateOneTimeTransaction(id, oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Override
    public void transactionsOnetimeIdTransferPost(String id) {
        oneTimeTransactionService.transferOneTimeTransactionAndSetStatus(id);
    }

    @Override
    public List<RecurringTransactionDto> transactionsRecurringGet(UUID sourceBankAccount, UUID targetBankAccount) {
        return recurringTransactionService.getRecurringTransactions().stream()
                .filter(transaction -> sourceBankAccount == null || (transaction.getTransfer().hasInternalSource() && transaction.getTransfer().getSourceBankAccountId().equals(sourceBankAccount.toString())))
                .filter(transaction -> targetBankAccount == null || (transaction.getTransfer().hasInternalTarget() && transaction.getTransfer().getTargetBankAccountId().equals(targetBankAccount.toString())))
                .map(recurringTransactionMapper::mapRecurringTransactionModelToDto)
                .toList();
    }

    @Override
    public void transactionsRecurringPost(RecurringTransactionDto recurringTransactionDto) {
        recurringTransactionService.createRecurringTransaction(recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));
    }

    @Override
    public void transactionsRecurringIdDelete(String id) {
        recurringTransactionService.deleteRecurringTransaction(id);
    }

    @Override
    public RecurringTransactionDto transactionsRecurringIdGet(String id) {
        final var recurringTransaction = recurringTransactionService.getRecurringTransaction(id).orElseThrow(NotFoundException::new);
        return recurringTransactionMapper.mapRecurringTransactionModelToDto(recurringTransaction);
    }

    @Override
    public void transactionsRecurringIdPatch(String id, RecurringTransactionDto recurringTransactionDto) {
        recurringTransactionService.updateRecurringTransaction(id, recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));
    }

    @Override
    public void transactionsRecurringTransactionIdRecordsPost(String transactionId, TransactionRecordDto transactionRecordDto) {
        recurringTransactionService.createTransactionRecordForRecurringTransaction(
                recurringTransactionMapper.mapTransactionRecordDtoToModel(transactionId, transactionRecordDto));
    }

    @Override
    public void transactionsRecurringTransactionIdRecordsRecordIdDelete(String transactionId, String recordId) {
        recurringTransactionService.deleteTransactionRecord(transactionId, recordId);
    }

    @Override
    public void transactionsRecurringTransactionIdRecordsRecordIdTransferPost(String transactionId, String recordId) {
        recurringTransactionService.transferTransactionRecordAndSetStatus(transactionId, recordId);
    }

}
