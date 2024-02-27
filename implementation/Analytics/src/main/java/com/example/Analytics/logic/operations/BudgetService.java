package com.example.Analytics.logic.operations;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.RecurringTransactionDto;
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
        BudgetPlan bp = spendingPerCategory();
        List<BudgetElement> oldBudgetList = bp.getBudgetElementList();
        List<BudgetElement> newBudgetList = createSavingsPlan(1200.0, bp).getBudgetElementList();
        double m1 = bp.getBudgetElementList().stream().map(be -> be.getMonetaryAmount().getAmount()).mapToDouble(Double::doubleValue).sum();
        double m2 = newBudgetList.stream().map(be -> be.getMonetaryAmount().getAmount()).mapToDouble(Double::doubleValue).sum();
        System.out.println("Total\t[" + m1 + "] - goal -> ["+m2+"]" );
        for (int index = 0; index < oldBudgetList.size(); index++) {
            System.out.println(oldBudgetList.get(index).getCategory().getName() + "\t["+ oldBudgetList.get(index).getMonetaryAmount().getAmount() + "] -> ["+newBudgetList.get(index).getMonetaryAmount().getAmount() + "€ ]");
        }
        System.out.println(newBudgetList.get(newBudgetList.size()-1).getCategory().getName() + " -> " + newBudgetList.get(newBudgetList.size()-1).getMonetaryAmount().getAmount());

    }

    public void fixedMonthlyTransactionChange(RecurringTransactionDto recurringTransactionDto, UpdateType updateType){
        //TODO implement
    }

    public BudgetPlan spendingPerCategory(){
        //todo ggf handle duplicates
        List<BudgetElement> budgetElementList = new ArrayList<>(history.stream().map(transaction ->
                BudgetElement.builder()
                .category(transaction.getCategory())
                .monetaryAmount(new MonetaryAmount(roundToTwoDecimalPlaces(transaction.calculateAmountAsAverage()))).build()
        ).toList());

        return BudgetPlan.builder()
                .startDate(LocalDate.now())
                .budgetElementList(budgetElementList)
                .id(UUID.randomUUID()).
                currentStatus(AchievementStatus.ACHIEVED)
                .build();
    }
    public BudgetPlan createSavingsPlan(double amountToBeSaved){
        return createSavingsPlan(amountToBeSaved, spendingPerCategory());
    }

    public BudgetPlan createSavingsPlan(double amountToBeSaved, BudgetPlan budgetPlan){
        if(budgetPlan.getBudgetElementList() == null || budgetPlan.getBudgetElementList().isEmpty())
            return BudgetPlan.builder().build();

        List<BudgetElement> budgetElementList = budgetPlan.getBudgetElementList();
        List<BudgetElement> newBudgetElements = new ArrayList<>();
        double totalAverageMonthlyAmount = budgetElementList.stream().map(budgetElement -> budgetElement.getMonetaryAmount().getAmount()).mapToDouble(Double::doubleValue).sum();
        double totalAverageMoneyToBeSaved = amountToBeSaved - totalAverageMonthlyAmount;
        double howMuchSpentOnAverage = Math.abs(budgetElementList.stream().map(budgetElement -> budgetElement.getMonetaryAmount().getAmount()).filter(money->money<0).mapToDouble(Double::doubleValue).sum());
        double missingAmount = (howMuchSpentOnAverage-totalAverageMoneyToBeSaved>0)?totalAverageMoneyToBeSaved:howMuchSpentOnAverage;

        if(totalAverageMoneyToBeSaved < 0){
            return BudgetPlan.builder().build();
        }

        for(BudgetElement budgetCatagory : budgetElementList){
            double amountToBeSavedInCategory = 0.0;
            if(budgetCatagory.getMonetaryAmount().getAmount() < 0){
                double weightOfCategory = Math.abs(budgetCatagory.getMonetaryAmount().getAmount())/howMuchSpentOnAverage;
                amountToBeSavedInCategory = roundToTwoDecimalPlaces(weightOfCategory*missingAmount);
            }
            newBudgetElements.add(BudgetElement.builder().category(budgetCatagory.getCategory()).monetaryAmount(new MonetaryAmount(amountToBeSavedInCategory)).build());
        }

        if(howMuchSpentOnAverage-totalAverageMoneyToBeSaved<0){
            double insufficientAmount = Math.abs(howMuchSpentOnAverage-totalAverageMoneyToBeSaved);
            BudgetElement newIncome = BudgetElement.builder()
                    .category(new Category("newIncome"))
                    .monetaryAmount(new MonetaryAmount(insufficientAmount))
                    .build();
            newBudgetElements.add(newIncome);
        }

        return new BudgetPlan(newBudgetElements);
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
                .amount(MonetaryAmountDto.builder().amount(roundToTwoDecimalPlaces(Math.random()*100.0)).build())
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

    private double roundToTwoDecimalPlaces(double value){
        return Math.round(value*100.0)/100.0;
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
                                "\n\tamount=" + reference.getAmount().getAmount() +
                                "\n\ttype=" + reference.getType() + "\n")
                );
            }
            System.out.println("\n\n");
        }
    }


}
