package com.example.Analytics.budgetFunctionality.api;

import com.example.Analytics.DateConverter;
import com.example.Analytics.Label;
import com.example.Analytics.budgetFunctionality.logic.model.*;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.RecurringTransactionDto;
import org.openapitools.client.model.TransferDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransactionMapper implements OneTimeTransactionMapper, RecurringTransactionMapper {

private DateConverter dateConverter;


    @Override
    public VariableMonthlyTransaction mapOneTimeTransactionDtoToModel(final OneTimeTransactionDto oneTimeTransactionDto) {

        TransferDto transfer = oneTimeTransactionDto.getTransfer();
        UUID source = (transfer == null || transfer.getSourceBankAccountId() == null) ? null : transfer.getSourceBankAccountId();
        UUID target = (transfer == null || transfer.getTargetBankAccountId() == null) ? null : transfer.getTargetBankAccountId();

        Transaction transaction = Transaction.builder()
                .id(oneTimeTransactionDto.getId())
                .date(dateConverter.stringToDate(oneTimeTransactionDto.getDate()))
                .name(oneTimeTransactionDto.getName())
                .transactionType(TransactionType.ONE_TIME_TRANSACTION)
                .sourceBankAccount(source)
                .targetBankAccount(target)
                .build();
        List<Transaction> referenceTransactions = new ArrayList<>();
        referenceTransactions.add(transaction);

        //TODO name ist gleich? -> finde die anderen monthly transactions
        return VariableMonthlyTransaction.builder()
                .id(UUID.randomUUID())
                .name(oneTimeTransactionDto.getName())
                .referenceTransactions(referenceTransactions)
                .averageAmount(getMonetaryAmountOfDto(oneTimeTransactionDto))
                .type(getTypeOfDto(oneTimeTransactionDto))
                //TODO what if type==shift?
                .build();
    }

    private double getMonetaryAmountOfDto(OneTimeTransactionDto oneTimeTransactionDto){
        if(oneTimeTransactionDto.getAmount() != null && oneTimeTransactionDto.getAmount().getAmount() != null){
            return oneTimeTransactionDto.getAmount().getAmount();
        }
        return 0.0;
    }

    private Type getTypeOfDto(OneTimeTransactionDto oneTimeTransactionDto){
        if(oneTimeTransactionDto.getType() == null)
            return null;
        return switch (oneTimeTransactionDto.getType()) {
            case SHIFT -> null;
            case EXPENSE -> Type.EXPENSE;
            case INCOME -> Type.INCOME;
        };
    }


    @Override
    public FixedMonthlyTransaction mapRecurringTransactionDtoToModel(final RecurringTransactionDto recurringTransactionDto) {
/*

        final var transactionRecords = recurringTransactionDto.getTransactionRecords() != null ? recurringTransactionDto.getTransactionRecords().stream()
                .map(dto -> mapTransactionRecordDtoToModel(recurringTransactionDto.getId().toString(), dto))
                .collect(Collectors.toSet()) : new HashSet<TransactionRecord>();

        return RecurringTransaction.with()
                .id(recurringTransactionDto.getId().toString())
                .name(recurringTransactionDto.getName())
                .description(recurringTransactionDto.getDescription())
                .type(mapTypeDtoToModel(recurringTransactionDto.getType()))
                .labels(mapLabelDtoToModel(recurringTransactionDto.getLabels()))
                .transfer(mapTransferDtoToModel(recurringTransactionDto.getTransfer(), recurringTransactionDto.getType()))
                .startDate(mapDateDtoToModel(recurringTransactionDto.getStartDate()))
                .periodicity(mapRecurringTypeModelToDto(recurringTransactionDto.getPeriodicity()))
                .fixedAmount(recurringTransactionDto.getFixedAmount() != null && recurringTransactionDto.getFixedAmount().getAmount() != null ? mapMonetaryAmountDtoToModel(recurringTransactionDto.getFixedAmount()) : null)
                .transactionRecords(transactionRecords)
                .build();
                */
        return null;

    }
    private List<String> mapLabelModelToDto(final Set<Label> labels) {
        return labels.stream().map(Label::name).toList();
    }

    private Set<Label> mapLabelDtoToModel(final List<String> labels) {
        if (labels == null) {
            return Collections.emptySet();
        }

        return labels.stream()
                .map(Label::new)
                .collect(Collectors.toSet());
    }

}
