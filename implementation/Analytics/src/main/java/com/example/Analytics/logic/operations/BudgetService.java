package com.example.Analytics.logic.operations;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.budgetModel.Transaction;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@RequiredArgsConstructor
@Service
public class BudgetService {
    private List<VariableMonthlyTransaction> variableTransactionHistory = new ArrayList<>();
    private List<FixedTransaction> fixedTransactionHistory = new ArrayList<>();
    private List<VariableMonthlyTransaction> currentMonth = new ArrayList<>();
    private final TransactionMapper transactionMapper;

    @Autowired
    private VariableMonthlyTransactionService variableMonthlyTransactionService;
    @Autowired
    private FixedTransactionService fixedTransactionService;


    public void variableMonthlyTransactionChange(OneTimeTransactionDto oneTimeTransactionDto, UpdateType updateType){
        createRandomVariableMonthlyTransactionEntry(5); // TODO delete

        List<VariableMonthlyTransaction> variableMonthlyTransactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDto);
        this.variableTransactionHistory = switch(updateType){
            case DELETE -> variableMonthlyTransactionService.variableMonthlyTransactionDelete(variableMonthlyTransactionList);
            case CREATE -> variableMonthlyTransactionService.variableMonthlyTransactionCreate(variableMonthlyTransactionList);
            case UPDATE -> variableMonthlyTransactionService.variableMonthlyTransactionUpdate(variableMonthlyTransactionList);
        };

        this.currentMonth = variableMonthlyTransactionService.updateMonthlyTransactions(this.variableTransactionHistory);

        printFunction(); //TODO remove
    }

    public void fixedTransactionChange(RecurringTransactionDto recurringTransactionDto, UpdateType updateType){
        List<FixedTransaction> fixedTransactionList = transactionMapper.recurrinMonthlyTransactionDtoToFixedTransacion(recurringTransactionDto);
        //TODO mapper
        this.fixedTransactionHistory = switch(updateType){
            case DELETE -> fixedTransactionService.fixedTransactionDelete(fixedTransactionList);
            case CREATE -> fixedTransactionService.fixedTransactionCreate(fixedTransactionList);
            case UPDATE -> fixedTransactionService.fixedTransactionUpdate(fixedTransactionList);
        };

        createRandomFixedTransactionEntry(5);

        System.out.println("#############################");
        for(FixedTransaction fixedTransaction : fixedTransactionHistory){
            System.out.println( "ID:\t"+
                    fixedTransaction.getId() + "\nPeriodicity:\t" +
                    fixedTransaction.getPeriodicity().name() + "\nname:\t" +
                    fixedTransaction.getCategory().getName() + "\n[ " +
                    fixedTransaction.calculateAverageAmount().getAmount() + "] \n"
            );
            for(Transaction trans : fixedTransaction.getReferenceTransactions()){
                System.out.println("\tAmount:\t" + trans.getAmount().getAmount()
                + "\n\tReferenceId:\t" + trans.getReferenceId()
                        + "\n\tDate:\t" + trans.getDate() );
            }
        }

    }

    public BudgetPlan oneTimeSpendingPerCategory(){
        //TODO add fixedTransactions
        //todo ggf handle duplicates
        List<BudgetElement> budgetElementList = new ArrayList<>(variableTransactionHistory.stream().map(transaction ->
                BudgetElement.builder()
                        .category(transaction.getCategory())
                        .monetaryAmount(new MonetaryAmount(roundToTwoDecimalPlaces(transaction.calculateAmountAsAverage()))).build()
        ).toList());

        return new BudgetPlan(budgetElementList);
    }

    public BudgetPlan spendingForEachCategory(){
        List<BudgetElement> mergedList = new ArrayList<>(oneTimeSpendingPerCategory().getBudgetElementList());

        for (BudgetElement budgetElement : fixedSpendingForCategories().getBudgetElementList()){
            for(BudgetElement existingElement : mergedList){
                if(budgetElement.getCategory().getName().equals(existingElement.getCategory().getName())){
                    double money = existingElement.getMonetaryAmount().getAmount() + budgetElement.getMonetaryAmount().getAmount();
                    existingElement.setMonetaryAmount(new MonetaryAmount(money));
                }
            }
        }
        return new BudgetPlan(mergedList);
    }

    private BudgetPlan fixedSpendingForCategories(){
        //todo ggf handle Category duplicates
        List<BudgetElement> fixedBudgetElementList =new ArrayList<>(
                fixedTransactionHistory.stream().map(transaction ->
                BudgetElement.builder()
                        .category(transaction.getCategory())
                        .monetaryAmount(transaction.calculateAverageAmount()).build()
        ).toList());

        return new BudgetPlan(fixedBudgetElementList);
    }

    public BudgetPlan createSavingsPlan(double amountToBeSaved){
        return createSavingsPlan(amountToBeSaved, oneTimeSpendingPerCategory());
    }

    public BudgetPlan createSavingsPlan(double amountToBeSaved, BudgetPlan oneTimeBudgetPlan){
        if(oneTimeBudgetPlan.getBudgetElementList() == null || oneTimeBudgetPlan.getBudgetElementList().isEmpty())
            return BudgetPlan.builder().build();
        //TODO take the fixed transaction money into account
        List<BudgetElement> budgetElementList = oneTimeBudgetPlan.getBudgetElementList();
        List<BudgetElement> fixedBudgetElementList = fixedSpendingForCategories().getBudgetElementList();
        List<BudgetElement> newBudgetElements = new ArrayList<>();
        double averageMonthlyOneTimeTransactions = budgetElementList.stream().mapToDouble(budgetElement -> budgetElement.getMonetaryAmount().getAmount()).sum();
        double totalAverageMonthlyAmount = averageMonthlyOneTimeTransactions;

        if(fixedBudgetElementList != null && !fixedBudgetElementList.isEmpty())
            totalAverageMonthlyAmount += fixedBudgetElementList.stream().mapToDouble(element -> element.getMonetaryAmount().getAmount()).sum();

        double totalAverageMoneyToBeSaved = amountToBeSaved - totalAverageMonthlyAmount;
        double howMuchSpentOnAverage = Math.abs(budgetElementList.stream().map(budgetElement -> budgetElement.getMonetaryAmount().getAmount()).filter(money->money<0).mapToDouble(Double::doubleValue).sum());
        double missingAmount = (howMuchSpentOnAverage-totalAverageMoneyToBeSaved>0)?totalAverageMoneyToBeSaved:howMuchSpentOnAverage;

        if(totalAverageMoneyToBeSaved < 0){//todo removeable?
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


    private void createRandomVariableMonthlyTransactionEntry(int amount){
        List<org.openapitools.model.OneTimeTransactionDto> randomDtoList = new ArrayList<>();

        randomDtoList.add(createRandomDtoForLabel(getRandomCategories(), UUID.fromString("497f6eca-3333-1111-bfab-53bbabab6a09")));

        for (int i = 0; i < amount; i++) {
            randomDtoList.add(createRandomDtoForLabel(getRandomCategories()));
        }

        for(org.openapitools.model.OneTimeTransactionDto randomDto : randomDtoList){
            List<VariableMonthlyTransaction> variableMonthlyTransactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(randomDto);
            variableMonthlyTransactionService.variableMonthlyTransactionCreate(variableMonthlyTransactionList);
        }
    }

    private void createRandomFixedTransactionEntry(int amount){
        List<RecurringTransactionDto> randomDtoList = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            randomDtoList.add(createRandomRecurringDtoForLabel(getRandomCategories()));
        }

        for(RecurringTransactionDto randomDto : randomDtoList){
            List<FixedTransaction> fixedTransactionList = transactionMapper.recurrinMonthlyTransactionDtoToFixedTransacion(randomDto);
            fixedTransactionService.fixedTransactionCreate(fixedTransactionList);
        }

    }
    private List<String> getRandomCategories(){
        List<String> wordList = new ArrayList<>();
        wordList.add("uni");
        wordList.add("schuhe");
        wordList.add("kleidung");
        wordList.add("essen");
        Collections.shuffle(wordList);
        int endValue = (int) Math.floor(Math.random()* wordList.size());

        return wordList.subList(0, endValue+1);
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
                .date(createRandomDate().toString())
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


    private int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private LocalDate createRandomDate() {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(2022, 2024);
        return LocalDate.of(year, month, day);
    }
    private RecurringTransactionDto createRandomRecurringDtoForLabel(List<String> labels){
        boolean randomSwapper = Math.random()<0.5;
        return org.openapitools.model.RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("randomName")
                .type(randomSwapper?TypeDto.INCOME:TypeDto.EXPENSE)
                .startDate(createRandomDate().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(roundToTwoDecimalPlaces(Math.random()*100.0)).build())
                .description("randomDescription")
                .transfer(org.openapitools.model.TransferDto.builder()
                        .sourceBankAccountId(UUID.randomUUID())
                        .targetBankAccountId(UUID.randomUUID())
                        .externalTargetId(UUID.randomUUID().toString())
                        .externalSourceId(UUID.randomUUID().toString())
                        .build())
                .labels(labels)
                .periodicity(randomSwapper?PeriodicityDto.MONTHLY:PeriodicityDto.YEARLY)
                .build();
    }

    private double roundToTwoDecimalPlaces(double value){
        return Math.round(value*100.0)/100.0;
    }

    private void printFunction(){
        //TODO delete
        System.out.println("----------history------------");
        debugPrint(variableTransactionHistory);
        /*
        System.out.println("----------currentMonth------------");
        debugPrint(currentMonth);
        System.out.println("----------------------------------");*/
        System.out.println("\n======================\n");

        debugPrintSpending(1200.0);
    }

    private void debugPrintSpending(double amount){
        BudgetPlan bp = oneTimeSpendingPerCategory();
        List<BudgetElement> oldBudgetList = bp.getBudgetElementList();
        List<BudgetElement> newBudgetList = createSavingsPlan(amount, bp).getBudgetElementList();
        double m1 = bp.getBudgetElementList().stream().map(be -> be.getMonetaryAmount().getAmount()).mapToDouble(Double::doubleValue).sum();
        double m2 = newBudgetList.stream().map(be -> be.getMonetaryAmount().getAmount()).mapToDouble(Double::doubleValue).sum();
        double monthlyIncome = this.fixedTransactionHistory.stream().mapToDouble(transaction -> transaction.calculateAverageAmount().getAmount()).sum();
        System.out.println("Total\t[" + m1 + "] - goal -> ["+m2+"]\t("+monthlyIncome+")" );
        for (int index = 0; index < oldBudgetList.size(); index++) {
            System.out.println(oldBudgetList.get(index).getCategory().getName() + "\t["+ oldBudgetList.get(index).getMonetaryAmount().getAmount() + "] -> ["+newBudgetList.get(index).getMonetaryAmount().getAmount() + "€ ]");
        }
        System.out.println(newBudgetList.get(newBudgetList.size()-1).getCategory().getName() + " -> " + newBudgetList.get(newBudgetList.size()-1).getMonetaryAmount().getAmount());
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
