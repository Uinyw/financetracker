package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.budgetModel.Category;
import com.example.Analytics.logic.model.budgetModel.Transaction;
import com.example.Analytics.logic.model.budgetModel.TransactionType;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.TransferDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {
    public List<VariableMonthlyTransaction> oneTimeTransactionDtoToVariableMonthlyTransaction(OneTimeTransactionDto oneTimeTransactionDto) {
        List<VariableMonthlyTransaction> variableMonthlyTransactionList = new ArrayList<>();

        if (oneTimeTransactionDto.getLabels() != null && oneTimeTransactionDto.getTransfer() != null) {
            variableMonthlyTransactionList = oneTimeTransactionDto.getLabels().stream()
                    .map(label -> createVariableMonthlyTransaction(label, oneTimeTransactionDto))
                    .toList();
        }

        return variableMonthlyTransactionList;
    }

    private Transaction createTransactionFromDto(OneTimeTransactionDto oneTimeTransactionDto) {
        TransferDto transfer = oneTimeTransactionDto.getTransfer();
        return Transaction.builder()
                .type(getType(oneTimeTransactionDto))
                .date(getLocalDateFromOneTimeTransactionDto(oneTimeTransactionDto))
                .bankAccountSource(transfer.getTargetBankAccountId())
                .bankAccountTarget(transfer.getTargetBankAccountId())
                .amount(getMonetaryAmountFromDto(oneTimeTransactionDto))
                .referenceId(oneTimeTransactionDto.getId())
                .id(UUID.randomUUID())
                .build();
    }

    private MonetaryAmount getMonetaryAmountFromDto(OneTimeTransactionDto oneTimeTransactionDto){
        MonetaryAmount money = new MonetaryAmount();
        if (oneTimeTransactionDto.getAmount() != null
                && oneTimeTransactionDto.getAmount().getAmount() != null) {
            money.setMoney(oneTimeTransactionDto.getAmount().getAmount().doubleValue());
        }

        return money;
    }

    private VariableMonthlyTransaction createVariableMonthlyTransaction(String label, OneTimeTransactionDto oneTimeTransactionDto) {
        Transaction transaction = createTransactionFromDto(oneTimeTransactionDto);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        VariableMonthlyTransaction oneTimeTransaction = VariableMonthlyTransaction.builder()
                .referenceTransactions(transactions)
                .category(new Category(label))
                .id(UUID.randomUUID())
                .name(label)
                .build();
        transaction.setVariableMonthlyTransaction(oneTimeTransaction);
        return oneTimeTransaction;
    }


    public LocalDate getLocalDateFromOneTimeTransactionDto(OneTimeTransactionDto oneTimeTransactionDto){
        if(oneTimeTransactionDto.getDate() == null)
            return null;
        LocalDate date = null;
        try{
            date = LocalDate.parse(oneTimeTransactionDto.getDate());
        }catch (Exception e){
            System.out.println(e);
        }

        return date;
    }
    public TransactionType getType(OneTimeTransactionDto oneTimeTransactionDto){
        if(oneTimeTransactionDto.getType() == null){
            return null;
        }
        return switch (oneTimeTransactionDto.getType()){
            case INCOME -> TransactionType.INCOME;
            case EXPENSE -> TransactionType.EXPENSE;
            case SHIFT -> null; //TODO how to deal with those?
        };
    }

}
