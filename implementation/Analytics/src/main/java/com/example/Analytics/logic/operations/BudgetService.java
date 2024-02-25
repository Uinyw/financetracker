package com.example.Analytics.logic.operations;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.*;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@RequiredArgsConstructor
@Service
public class BudgetService {
    private List<VariableMonthlyTransaction> history;
    private List<VariableMonthlyTransaction> currentMonth;
    @Autowired
    private VariableMonthlyTransactionService variableMonthlyTransactionService;
    private final TransactionMapper transactionMapper;


    private void variableMonthlyTransactionCreate(List<VariableMonthlyTransaction> variableMonthlyTransactionList){
        this.history = variableMonthlyTransactionService.variableMonthlyTransactionCreate(variableMonthlyTransactionList);
        this.currentMonth = variableMonthlyTransactionService.updateMonthlyTransactions(this.history);
        printFunction();
    }

    private void variableMonthlyTransactionDelete(List<VariableMonthlyTransaction> variableMonthlyTransactionList){
        this.history = variableMonthlyTransactionService.variableMonthlyTransactionDelete(variableMonthlyTransactionList);
        this.currentMonth = variableMonthlyTransactionService.updateMonthlyTransactions(this.history);
    }

    public void variableMonthlyTransactionChange(OneTimeTransactionDto oneTimeTransactionDto, UpdateType updateType){
        List<VariableMonthlyTransaction> variableMonthlyTransactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDTOMapping(oneTimeTransactionDto));
        //TODO test update and delete
        switch(updateType){
            case DELETE -> variableMonthlyTransactionDelete(variableMonthlyTransactionList);
            case CREATE -> variableMonthlyTransactionCreate(variableMonthlyTransactionList);
            case UPDATE -> {
                variableMonthlyTransactionDelete(variableMonthlyTransactionList);
                variableMonthlyTransactionCreate(variableMonthlyTransactionList);
            }
        }

    }

    public void fixedMonthlyTransactionChange(RecurringTransactionDto recurringTransactionDto, UpdateType updateType){
        //TODO implement
    }

    private void calculateSavingPerCategory(){
        //TODO implement
    }

    private void printFunction(){
        System.out.println("----------history------------");
        debugPrint(history);
        System.out.println("----------currentMonth------------");
        debugPrint(currentMonth);
        System.out.println("----------------------------------");
    }
    private void debugPrint(List<VariableMonthlyTransaction> prints){
        for(VariableMonthlyTransaction variableMonthlyTransaction : prints){
            System.out.println("\t[Object]\nID:\t"+ variableMonthlyTransaction.getId()
                    + "\nname:\t" + variableMonthlyTransaction.getName()
                    + "\ncategory:\t" + variableMonthlyTransaction.getCategory().getName()
                    + "\n[AvgSpending:\t" + variableMonthlyTransaction.calculateAmountAsAverage() + "]"
            );
            if(variableMonthlyTransaction.getReferenceTransactions() != null && !variableMonthlyTransaction.getReferenceTransactions().isEmpty()){
                variableMonthlyTransaction.getReferenceTransactions().forEach(reference ->
                        System.out.println("\tTransaction Details: " +
                                "\n\tid=\t" + reference.getId() +
                                "\n\treferenceId=" + reference.getReferenceId() +
                                "\n\tdate=" + reference.getDate() +
                                "\n\tbankAccountSource=" + reference.getBankAccountSource() +
                                "\n\tbankAccountTarget=" + reference.getBankAccountTarget() +
                                "\n\tamount=" + reference.getAmount().getMoney() +
                                "\n\ttype=" + reference.getType() + "\n")
                );
            }
            System.out.println("\n\n");
        }
    }


    private org.openapitools.client.model.OneTimeTransactionDto oneTimeTransactionDTOMapping(OneTimeTransactionDto oneTimeTransactionDto){
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
