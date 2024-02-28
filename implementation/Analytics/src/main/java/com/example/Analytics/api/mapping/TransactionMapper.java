package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import org.openapitools.client.model.*;
import org.openapitools.model.BudgetAchievementStatus;
import org.openapitools.model.BudgetPlanDTO;
import org.springframework.stereotype.Component;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.RecurringTransactionDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<FixedTransaction> recurrinMonthlyTransactionDtoToFixedTransacion(org.openapitools.client.model.RecurringTransactionDto recurringTransactionDto){
        List<FixedTransaction> fixedTransactionList = new ArrayList<>();

        if (recurringTransactionDto.getLabels() != null && recurringTransactionDto.getTransfer() != null) {
            fixedTransactionList = recurringTransactionDto.getLabels().stream()
                    .map(label -> createFixedTransaction(label, recurringTransactionDto))
                    .toList();
        }

        return fixedTransactionList;
    }

    public List<FixedTransaction> recurrinMonthlyTransactionDtoToFixedTransacion(RecurringTransactionDto recurringTransactionDto){
        List<FixedTransaction> fixedTransactionList = new ArrayList<>();

        if (recurringTransactionDto.getLabels() != null && recurringTransactionDto.getTransfer() != null) {
            fixedTransactionList = recurringTransactionDto.getLabels().stream()
                    .map(label -> createFixedTransaction(label, recurringTransactionDto))
                    .toList();
        }

        return fixedTransactionList;
    }

    private Transaction createTransactionFromDto(RecurringTransactionDto reoccuringTransactionDto) {
        org.openapitools.model.TransferDto transfer = reoccuringTransactionDto.getTransfer();
        return Transaction.builder()
                .type(getType(reoccuringTransactionDto))
                .date(getLocalDateFromTransactionDto(reoccuringTransactionDto))
                .bankAccountSource(transfer.getTargetBankAccountId())
                .bankAccountTarget(transfer.getTargetBankAccountId())
                .amount(getMonetaryAmountFromDto(reoccuringTransactionDto))
                .referenceId(reoccuringTransactionDto.getId())
                .id(UUID.randomUUID())
                .build();
    }

    private Transaction createTransactionFromDto(OneTimeTransactionDto oneTimeTransactionDto) {
        org.openapitools.model.TransferDto transfer = oneTimeTransactionDto.getTransfer();
        return Transaction.builder()
                .type(getType(oneTimeTransactionDto))
                .date(getLocalDateFromTransactionDto(oneTimeTransactionDto))
                .bankAccountSource(transfer.getTargetBankAccountId())
                .bankAccountTarget(transfer.getTargetBankAccountId())
                .amount(getMonetaryAmountFromDto(oneTimeTransactionDto))
                .referenceId(oneTimeTransactionDto.getId())
                .id(UUID.randomUUID())
                .build();
    }

    public BudgetPlanDTO budgetPlanToDto(BudgetPlan budgetPlan){
        return BudgetPlanDTO.builder()
                .id(budgetPlan.getId())
                .achievementStatus(achievementStatusToDto(budgetPlan.getCurrentStatus()))
                .date(budgetPlan.getStartDate().toString())
                .plan(budgetPlan.getBudgetElementList().stream().map(this::budgetElementToDto).toList())
                .build();
    }
    private BudgetAchievementStatus achievementStatusToDto(AchievementStatus achievementStatus){
        return switch (achievementStatus){
            case FAILED -> BudgetAchievementStatus.FAILED;
            case ACHIEVED -> BudgetAchievementStatus.ACHIEVED;
        };
    }
    private org.openapitools.model.BudgetElementDto budgetElementToDto(BudgetElement budgetElement){
        return org.openapitools.model.BudgetElementDto.builder()
                .category(budgetElement.getCategory().getName())
                .monetaryAmount(monetaryAmountToDto(budgetElement.getMonetaryAmount()))
                .build();
    }

    private org.openapitools.model.MonetaryAmountDto monetaryAmountToDto(MonetaryAmount monetaryAmount){
        return org.openapitools.model.MonetaryAmountDto.builder().amount(monetaryAmount.getAmount()).build();
    }

    private MonetaryAmount getMonetaryAmountFromDto(OneTimeTransactionDto oneTimeTransactionDto){
        MonetaryAmount money = new MonetaryAmount();
        if (oneTimeTransactionDto.getAmount() != null
                && oneTimeTransactionDto.getAmount().getAmount() != null) {
            money.setAmount(oneTimeTransactionDto.getAmount().getAmount().doubleValue());
        }

        return money;
    }
    private MonetaryAmount getMonetaryAmountFromDto(RecurringTransactionDto recurringTransactionDto){
        MonetaryAmount money = new MonetaryAmount();
        if (recurringTransactionDto.getFixedAmount() != null
                && recurringTransactionDto.getFixedAmount().getAmount() != null) {
            money.setAmount(recurringTransactionDto.getFixedAmount().getAmount().doubleValue());
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

    private FixedTransaction createFixedTransaction(String label, RecurringTransactionDto recurringTransactionDto) {
        //for all labels one transaction
        Transaction transaction = createTransactionFromDto(recurringTransactionDto);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        FixedTransaction fixedTransaction = FixedTransaction.builder()
                .referenceTransactions(transactions)
                .periodicity(periodicityDtoToModel(recurringTransactionDto.getPeriodicity()))
                .category(new Category(label))
                .id(UUID.randomUUID())
                .name(label)
                .build();
        transaction.setFixedTransaction(fixedTransaction);
        return fixedTransaction;
    }

    private FixedTransaction createFixedTransaction(String label, RecurringTransactionDto recurringTransactionDto) {
        //for all labels one transaction
        Transaction transaction = createTransactionFromDto(recurringTransactionDto);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        FixedTransaction fixedTransaction = FixedTransaction.builder()
                .referenceTransactions(transactions)
                .periodicity(periodicityDtoToModel(recurringTransactionDto.getPeriodicity()))
                .category(new Category(label))
                .id(UUID.randomUUID())
                .name(label)
                .build();
        transaction.setFixedTransaction(fixedTransaction);
        return fixedTransaction;
    }

    private Periodicity periodicityDtoToModel(org.openapitools.model.PeriodicityDto periodicityDto){
        return switch (periodicityDto){
            case HALF_YEARLY -> Periodicity.HALF_YEARLY;
            case QUARTERLY -> Periodicity.QUARTERLY;
            case YEARLY -> Periodicity.YEARLY;
            case MONTHLY -> Periodicity.MONTHLY;
        };
    }


    private LocalDate getLocalDateFromTransactionDto(OneTimeTransactionDto oneTimeTransactionDto){
        if(oneTimeTransactionDto.getDate() == null)
            return null;
        return getLocalDateFromString(oneTimeTransactionDto.getDate());
    }
    private LocalDate getLocalDateFromTransactionDto(RecurringTransactionDto oneTimeTransactionDto){
        if(oneTimeTransactionDto.getStartDate() == null)
            return null;
        return getLocalDateFromString(oneTimeTransactionDto.getStartDate());
    }
    private LocalDate getLocalDateFromString(String dateString){
        LocalDate date = null;
        try{
            date = LocalDate.parse(dateString);
        }catch (Exception e){
            System.out.println(e);
        }
        return date;
    }
    private TransactionType getType(OneTimeTransactionDto oneTimeTransactionDto){
        if(oneTimeTransactionDto.getType() == null)
            return null;

        return getType(oneTimeTransactionDto.getType());
    }
    private TransactionType getType(org.openapitools.model.TypeDto typeDto){
        return switch (typeDto){
            case INCOME -> TransactionType.INCOME;
            case EXPENSE -> TransactionType.EXPENSE;
            case SHIFT -> null; //TODO how to deal with those?
        };
    }
    private TransactionType getType(RecurringTransactionDto recurringTransactionDto){
        if(recurringTransactionDto.getType() == null)
            return null;

        return getType(recurringTransactionDto.getType());
    }



}
