package com.example.Analytics.DietFunctionality.BudgetFunctionality;

import com.example.Analytics.AchievementStatus;
import com.example.Analytics.Category;
import com.example.Analytics.Type;
import org.openapitools.model.MonetaryAmount;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Budget {
    private UUID id;
    private List<VariableMonthlyTransactions> history;
    private List<VariableMonthlyTransactions> currentMonth;
    private List<FixedMonthlyTransaction> reoccurringExpenses;
    private MonetaryAmount amount; //how much is there after everything

    //TODO budgetPlanDTO?
    //TODO budget wie viel jeden monat gespeichert werden soll
    public BudgetPlan createBudgetPlan(MonetaryAmount desiredVariableAmount){
        //bankaccount als ausgabe aus nichts und eingabe als nichts - shifts werden nicht betrachtet
        //TODO logic besprechen
        //Nur aktueller monat
        double totalMoney = getFixedMonthlyAmount() + getVariableMonthlyIncomse(history);
        double averageExpenses = getVariableMonthlyExpenses(history);
        double averageSpendature = getFixedMonthlyAmount() + getVariableMonthlyAmount(history);

        double alreadySpend = getVariableMonthlyAmount(currentMonth);
        double moneyLeftTillMonthEnd = totalMoney - (alreadySpend + desiredVariableAmount.getAmount());
        boolean completed = moneyLeftTillMonthEnd >= 0;
        double leftToSpend = moneyLeftTillMonthEnd;


        //1. money left < 0
        //2. money left > 0
        //TODO this is only equal distribution -> todo fix
        Map<Category, MonetaryAmount> categoryMonetaryAmountMap = new HashMap<>();
        double amountPerCategory = moneyLeftTillMonthEnd / currentMonth.size();
        for (VariableMonthlyTransactions transaction : currentMonth) {
            MonetaryAmount money = new MonetaryAmount();
            if(transaction.getType().equals(Type.EXPENSE)){
                money.setAmount(transaction.getAverageAmount()-amountPerCategory);
            }else{
                money.setAmount(transaction.getAverageAmount()+amountPerCategory);
            }
            categoryMonetaryAmountMap.put(transaction.getCategory(), money);
        }
        return BudgetPlan.builder().id(UUID.randomUUID())
                .plan(categoryMonetaryAmountMap)
                .currentstatus(completed? AchievementStatus.ACHIEVED:AchievementStatus.FAILED)
                .startDate(LocalDate.now())
                .build();

    }

    private double getVariableMonthlyIncomse(List<VariableMonthlyTransactions> variableTransaction){
        return variableTransaction.stream().map(transaction->
                        (transaction.getType() == Type.INCOME)?
                                0.0:
                                transaction.calculateAmountAsAverage())
                .reduce(0.0, Double::sum);
    }
    private double getVariableMonthlyExpenses(List<VariableMonthlyTransactions> variableTransaction){
        return variableTransaction.stream().map(transaction->
                        (transaction.getType() == Type.EXPENSE)?
                                0.0:
                                transaction.calculateAmountAsAverage())
                .reduce(0.0, Double::sum);
    }

    private double getVariableMonthlyAmount(List<VariableMonthlyTransactions> variableTransaction){
        return variableTransaction.stream().map(transaction->
                        (transaction.getType() == Type.EXPENSE)?
                                -transaction.calculateAmountAsAverage():
                                transaction.calculateAmountAsAverage())
                .reduce(0.0, Double::sum);
    }
    private double getFixedMonthlyAmount(){
        return reoccurringExpenses.stream().map(transaction -> (
                transaction.getReferenceTransaction().getMonetaryAmount().getAmount() != null)?
                0.0 :
                (transaction.getType()== Type.INCOME)?
                        transaction.getReferenceTransaction().getMonetaryAmount().getAmount():
                        -transaction.getReferenceTransaction().getMonetaryAmount().getAmount()
        ).reduce(0.0, Double::sum);
    }
}
