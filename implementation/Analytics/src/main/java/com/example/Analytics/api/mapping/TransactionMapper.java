package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import org.openapitools.client.model.*;
import org.openapitools.model.BudgetAchievementStatus;
import org.openapitools.model.BudgetPlanDTO;
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

    public List<VariableMonthlyTransaction> oneTimeTransactionDtoToVariableMonthlyTransaction(org.openapitools.model.OneTimeTransactionDto oneTimeTransactionDto){
        return oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDTOMapping(oneTimeTransactionDto));
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


    private org.openapitools.client.model.OneTimeTransactionDto oneTimeTransactionDTOMapping(org.openapitools.model.OneTimeTransactionDto oneTimeTransactionDto){
        return org.openapitools.client.model.OneTimeTransactionDto.builder()
                .id(oneTimeTransactionDto.getId())
                .type(typeDtoMapper(oneTimeTransactionDto.getType()))
                .date(oneTimeTransactionDto.getDate())
                .name(oneTimeTransactionDto.getName())
                .amount(typeDtoMapper(oneTimeTransactionDto.getAmount()))
                .description(oneTimeTransactionDto.getDescription())
                .labels(oneTimeTransactionDto.getLabels())
                .transfer(typeDtoMapper(oneTimeTransactionDto.getTransfer()))
                .transferStatus(typeDtoMapper(oneTimeTransactionDto.getTransferStatus()))
                .build();

    }

    private TransferStatusDto typeDtoMapper(org.openapitools.model.TransferStatusDto transferStatusDto){
        return switch (transferStatusDto){
            case INITIAL -> TransferStatusDto.INITIAL;
            case FAILED -> TransferStatusDto.FAILED;
            case SUCCESSFUL -> TransferStatusDto.SUCCESSFUL;
        };
    }
    private TransferDto typeDtoMapper(org.openapitools.model.TransferDto transferDto){
        return TransferDto.builder()
                .sourceBankAccountId(transferDto.getSourceBankAccountId())
                .externalTargetId(transferDto.getExternalTargetId())
                .targetBankAccountId(transferDto.getTargetBankAccountId())
                .externalSourceId(transferDto.getExternalSourceId())
                .build();
    }

    private MonetaryAmountDto typeDtoMapper(org.openapitools.model.MonetaryAmountDto monetaryAmountDto){
        return MonetaryAmountDto.builder().amount(monetaryAmountDto.getAmount()).build();
    }

    private TypeDto typeDtoMapper(org.openapitools.model.TypeDto typeDto){
        return switch (typeDto){
            case SHIFT -> TypeDto.SHIFT;
            case EXPENSE -> TypeDto.EXPENSE;
            case INCOME -> TypeDto.INCOME;
        };
    }


}
