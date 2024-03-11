package com.example.Analytics.logic.operations;

import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BudgetService {
    private List<VariableMonthlyTransaction> variableTransactionHistory = new ArrayList<>();
    private List<FixedTransaction> fixedTransactionHistory = new ArrayList<>();
    private List<VariableMonthlyTransaction> currentMonth = new ArrayList<>();

    @Autowired
    private VariableMonthlyTransactionService variableMonthlyTransactionService;
    @Autowired
    private FixedTransactionService fixedTransactionService;


    public void variableMonthlyTransactionChange(List<VariableMonthlyTransaction> variableMonthlyTransactionList, UpdateType updateType){
        this.variableTransactionHistory = switch(updateType){
            case DELETE -> variableMonthlyTransactionService.variableMonthlyTransactionDelete(variableMonthlyTransactionList);
            case CREATE -> variableMonthlyTransactionService.variableMonthlyTransactionCreate(variableMonthlyTransactionList);
            case UPDATE -> variableMonthlyTransactionService.variableMonthlyTransactionUpdate(variableMonthlyTransactionList);
        };

        this.currentMonth = variableMonthlyTransactionService.updateMonthlyTransactions(this.variableTransactionHistory);
    }

    public void fixedTransactionChange(List<FixedTransaction> fixedTransactionList, UpdateType updateType){
        this.fixedTransactionHistory = switch(updateType){
            case DELETE -> fixedTransactionService.fixedTransactionDelete(fixedTransactionList);
            case CREATE -> fixedTransactionService.fixedTransactionCreate(fixedTransactionList);
            case UPDATE -> fixedTransactionService.fixedTransactionUpdate(fixedTransactionList);
        };
    }

    public BudgetPlan createSavingsPlan(double amountToBeSaved, FilterElement filterElement){
        return createSavingsPlan(amountToBeSaved, oneTimeSpendingPerCategory(), fixedSpendingForCategories(), filterElement);
    }

    private BudgetPlan createSavingsPlan(double amountToBeSaved, BudgetPlan oneTimeBudgetPlan, BudgetPlan recurringBudgetPlan, FilterElement filterElement){
        List<BudgetElement> budgetElementList = getBudgetElementsAndFilterIfExists(oneTimeBudgetPlan, filterElement);
        List<BudgetElement> fixedBudgetElementList = getBudgetElementsAndFilterIfExists(recurringBudgetPlan, filterElement);

        if(budgetElementList.isEmpty() && fixedBudgetElementList.isEmpty())
            return new BudgetPlan();

        //TODO adjust calculation
        List<BudgetElement> newBudgetElements = new ArrayList<>();
        double averageMonthlyOneTimeTransactions = budgetElementList.stream().mapToDouble(budgetElement -> budgetElement.getMonetaryAmount().getAmount()).sum();
        double totalAverageMonthlyAmount = averageMonthlyOneTimeTransactions;

        if(!fixedBudgetElementList.isEmpty())
            totalAverageMonthlyAmount += fixedBudgetElementList.stream().mapToDouble(element -> element.getMonetaryAmount().getAmount()).sum();

        double totalAverageMoneyToBeSaved = amountToBeSaved - totalAverageMonthlyAmount;
        double howMuchSpentOnAverage = Math.abs(budgetElementList.stream().map(budgetElement -> budgetElement.getMonetaryAmount().getAmount()).filter(money->money<0).mapToDouble(Double::doubleValue).sum());
        double missingAmount = (howMuchSpentOnAverage-totalAverageMoneyToBeSaved>0)?totalAverageMoneyToBeSaved:howMuchSpentOnAverage;

        if(totalAverageMoneyToBeSaved < 0){//todo removeable?
            newBudgetElements.addAll(budgetElementList);
            newBudgetElements.addAll(fixedBudgetElementList);
            return new BudgetPlan(newBudgetElements);
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

    public BudgetPlan spendingForEachCategory(FilterElement filter){
        List<BudgetElement> mergedList = new ArrayList<>(oneTimeSpendingPerCategory().getBudgetElementList());

        for (BudgetElement budgetElement : fixedSpendingForCategories().getBudgetElementList()){
            boolean mergergedValue = false;
            for(BudgetElement existingElement : mergedList){
                if(budgetElement.getCategory().getName().equals(existingElement.getCategory().getName())){
                    double money = existingElement.getMonetaryAmount().getAmount() + budgetElement.getMonetaryAmount().getAmount();
                    existingElement.setMonetaryAmount(new MonetaryAmount(money));
                    mergergedValue = true;
                    break;
                }
            }
            if(!mergergedValue){
                mergedList.add(budgetElement);
            }
        }
        BudgetPlan completeBudgetPlan = new BudgetPlan(mergedList);
        return new BudgetPlan(getBudgetElementsAndFilterIfExists(completeBudgetPlan, filter));
    }

    private BudgetPlan oneTimeSpendingPerCategory(){
        //TODO add fixedTransactions
        //todo ggf handle duplicates
        List<BudgetElement> budgetElementList = new ArrayList<>(variableTransactionHistory.stream().map(transaction ->
                BudgetElement.builder()
                        .category(transaction.getCategory())
                        .monetaryAmount(new MonetaryAmount(roundToTwoDecimalPlaces(transaction.calculateAmountAsAverage()))).build()
        ).toList());

        return new BudgetPlan(budgetElementList);
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



    private boolean containsCategory(Category category, List<Category> categoryList){
        if(categoryList.isEmpty())
            return true;

        boolean containsCategory = false;
        for(Category cat : categoryList){
            if(cat.getName().equals(category.getName()))
                containsCategory = true;
        }
        return containsCategory;
    }



    private List<BudgetElement> getBudgetElementsAndFilterIfExists(BudgetPlan budgetPlan, FilterElement filterElement){
        if (budgetPlan == null)
            return new ArrayList<>();
        return budgetPlan.getBudgetElementList().stream().filter(x->containsCategory(x.getCategory(), filterElement.getCategoryList())).toList();
    }

    private double roundToTwoDecimalPlaces(double value){
        return Math.round(value*100.0)/100.0;
    }

}
