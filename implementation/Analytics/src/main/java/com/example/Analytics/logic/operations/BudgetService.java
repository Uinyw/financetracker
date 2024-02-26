package com.example.Analytics.logic.operations;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.Category;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import com.example.Analytics.logic.model.generalModel.MoneyPerCategory;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.*;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferStatusDto;
import org.openapitools.model.TypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    }

    private void variableMonthlyTransactionDelete(List<VariableMonthlyTransaction> variableMonthlyTransactionList){
        this.history = variableMonthlyTransactionService.variableMonthlyTransactionDelete(variableMonthlyTransactionList);
        this.currentMonth = variableMonthlyTransactionService.updateMonthlyTransactions(this.history);
    }

    public void variableMonthlyTransactionChange(OneTimeTransactionDto oneTimeTransactionDto, UpdateType updateType){
        List<VariableMonthlyTransaction> variableMonthlyTransactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto);
        //TODO test update and delete
        switch(updateType){
            case DELETE -> variableMonthlyTransactionDelete(variableMonthlyTransactionList);
            case CREATE -> variableMonthlyTransactionCreate(variableMonthlyTransactionList);
            case UPDATE -> {
                variableMonthlyTransactionDelete(variableMonthlyTransactionList);
                variableMonthlyTransactionCreate(variableMonthlyTransactionList);
            }
        }

        printFunction();
        System.out.println("\n======================\n");
        List<MoneyPerCategory> tmp = calculateSavingPerCategory(1200.0);
        double m1 = tmp.stream().map(MoneyPerCategory::getMoney).mapToDouble(Double::doubleValue).sum();
        double m2 = tmp.stream().map(MoneyPerCategory::getMoneyToBeSaved).mapToDouble(Double::doubleValue).sum();
        System.out.println("Total\t[" + m1 + "] - goal -> ["+m2+"]" );
        for(MoneyPerCategory moneyPerCategory : tmp)
            System.out.println(moneyPerCategory.getCategory().getName() + "\t["+ moneyPerCategory.getMoney() + "] -> ["+moneyPerCategory.getMoneyToBeSaved() + "€ ]");
    }

    public void fixedMonthlyTransactionChange(RecurringTransactionDto recurringTransactionDto, UpdateType updateType){
        //TODO implement
    }

    public List<MoneyPerCategory> moneySpendPerCategory(){
        //todo ggf handle duplicates
        return new ArrayList<>(history.stream().map(transaction -> new MoneyPerCategory(transaction.getCategory(), transaction.calculateAmountAsAverage())).toList());
    }

    public List<MoneyPerCategory> calculateSavingPerCategory(double amountToBeSaved){
        //TODO return a budgetplan DTO
        List<MoneyPerCategory> moneyPerCategories = moneySpendPerCategory();
        double totalAverageMonthlyAmount = moneyPerCategories.stream().map(MoneyPerCategory::getMoney).mapToDouble(Double::doubleValue).sum();
        double totalAverageMoneyToBeSaved = amountToBeSaved - totalAverageMonthlyAmount;
        if(totalAverageMoneyToBeSaved < 0){
            return new ArrayList<>();
        }
        double howMuchSpentOnAverage = Math.abs(moneyPerCategories.stream().map(MoneyPerCategory::getMoney).filter(money->money<0).mapToDouble(Double::doubleValue).sum());
        double missingAmount = (howMuchSpentOnAverage-totalAverageMoneyToBeSaved>0)?totalAverageMoneyToBeSaved:howMuchSpentOnAverage;
        for(MoneyPerCategory moneyCatagory : moneyPerCategories){
            if(moneyCatagory.getMoney() > 0){
                moneyCatagory.setMoneyToBeSaved(0);
                continue;
            }
            double weightOfCategory = Math.abs(moneyCatagory.getMoney())/howMuchSpentOnAverage;
            moneyCatagory.setMoneyToBeSaved(weightOfCategory*missingAmount);
        }

        if(howMuchSpentOnAverage-totalAverageMoneyToBeSaved<0){
            MoneyPerCategory moneyPerCategory = new MoneyPerCategory(new Category("newIncome"),0.0);
            moneyPerCategory.setMoneyToBeSaved(Math.abs(howMuchSpentOnAverage-totalAverageMoneyToBeSaved));
            moneyPerCategories.add(moneyPerCategory);
        }

        //TODO implement
        return moneyPerCategories;
    }

    public void createRandomEntry(int amount){
        List<org.openapitools.model.OneTimeTransactionDto> randomDtoList = new ArrayList<>();
        List<String> wordList = new ArrayList<>();
        wordList.add("uni");
        wordList.add("schuhe");
        wordList.add("kleidung");
        wordList.add("essen");

        randomDtoList.add(createRandomDtoForLabel(wordList.subList(0, 3), UUID.fromString("497f6eca-3333-1111-bfab-53bbabab6a09")));

        Collections.shuffle(wordList);
        for (int i = 0; i < amount; i++) {
            int endValue = (int) Math.floor(Math.random()* wordList.size());
            randomDtoList.add(createRandomDtoForLabel(wordList.subList(0, endValue+1)));
        }


        for(org.openapitools.model.OneTimeTransactionDto randomDto : randomDtoList){
            List<VariableMonthlyTransaction> variableMonthlyTransactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(randomDto);
            variableMonthlyTransactionCreate(variableMonthlyTransactionList);
        }
    }

    private org.openapitools.model.OneTimeTransactionDto createRandomDtoForLabel(List<String> labels){
        return createRandomDtoForLabel(labels, UUID.randomUUID());
    }

    private org.openapitools.model.OneTimeTransactionDto createRandomDtoForLabel(List<String> labels, UUID uuid){
        boolean randomSwapper = Math.random()<0.5;
        return org.openapitools.model.OneTimeTransactionDto.builder()
                .id(uuid)
                .name("randomName")
                .type(randomSwapper?TypeDto.INCOME:TypeDto.EXPENSE)
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(Math.round(Math.random()*10000.0)/100.0).build())
                .description("randomDescription")
                .transfer(org.openapitools.model.TransferDto.builder()
                        .sourceBankAccountId(UUID.randomUUID())
                        .targetBankAccountId(UUID.randomUUID())
                        .externalTargetId(UUID.randomUUID().toString())
                        .externalSourceId(UUID.randomUUID().toString())
                        .build())
                .labels(labels)
                .transferStatus(TransferStatusDto.SUCCESSFUL)
                .build();
    }

    private void printFunction(){
        //TODO delete
        System.out.println("----------history------------");
        debugPrint(history);
        /*
        System.out.println("----------currentMonth------------");
        debugPrint(currentMonth);
        System.out.println("----------------------------------");*/
    }
    private void debugPrint(List<VariableMonthlyTransaction> prints){
        //TODO delete
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


}
