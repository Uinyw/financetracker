package com.example.Analytics.BudgetFunctionality;

import com.example.Analytics.AchievementStatus;
import com.example.Analytics.Category;
import com.example.Analytics.Type;
import org.openapitools.client.model.MonetaryAmount;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Component
public class Budget {
    private UUID id;
    private List<VariableMonthlyTransactions> history;
    private List<VariableMonthlyTransactions> currentMonth;
    private List<FixedMonthlyTransaction> reoccurringExpenses;
    private MonetaryAmount amount; //how much is there after everything
    //TODO was machst amount konkret

    //TODO budgetPlanDTO?
    //TODO budget wie viel jeden monat gespeichert werden soll
    public BudgetPlan createBudgetPlan(MonetaryAmount desiredVariableAmount){
        //bankaccount als ausgabe aus nichts und eingabe als nichts - shifts werden nicht betrachtet
        //TODO logic besprechen
        //Nur aktueller monat
        double totalMoney = getFixedMonthlyAmount() + getVariableMonthlyIncomes(history);
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
                .currentStatus(completed? AchievementStatus.ACHIEVED:AchievementStatus.FAILED)
                .startDate(Date.from(localDateToInstant(LocalDate.now())))
                .build();

    }


    public void budgetUpdate(List<VariableMonthlyTransactions> history,
                              List<FixedMonthlyTransaction> reoccurringExpenses){
        this.history = history;
        this.currentMonth = getCurrentMonth(history);
        this.reoccurringExpenses = reoccurringExpenses;
    }


    private double getVariableMonthlyIncomes(List<VariableMonthlyTransactions> variableTransaction){
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

    public List<VariableMonthlyTransactions> getCurrentMonth(List<VariableMonthlyTransactions> transactions){
        List<VariableMonthlyTransactions> currentMonth = new ArrayList<>();
        for(VariableMonthlyTransactions transaction : transactions){
            List<Transaction> transactionThisMonth = transaction.getReferenceTransactions()
                    .stream().filter(trans ->
                            getMonthDifference(dateToLocalDate(trans.getDate()), LocalDate.now()) > 0
                    ).toList();
            if(!transactionThisMonth.isEmpty())
                currentMonth.add(VariableMonthlyTransactions.builder()
                        .name(transaction.getName())
                        .id(UUID.randomUUID())
                        .referenceTransactions(transactionThisMonth)
                        .type(transaction.getType())
                        .category(transaction.getCategory())
                        .build());
        }
        return currentMonth;
    }

    private int getMonthDifference(LocalDate date1, LocalDate date2){
        Period time = Period.between(date1, date2);
        int months = Math.abs(time.getMonths());
        int years = Math.abs(time.getYears());
        return months+years*12;
    }

    private LocalDate dateToLocalDate(Date date){
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Instant localDateToInstant(LocalDate localDate){
        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    }
}
