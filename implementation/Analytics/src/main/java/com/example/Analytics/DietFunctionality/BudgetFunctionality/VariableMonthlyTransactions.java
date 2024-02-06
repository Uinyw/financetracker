package com.example.Analytics.DietFunctionality.BudgetFunctionality;

import com.example.Analytics.Category;
import com.example.Analytics.Type;
import lombok.Getter;
import org.openapitools.model.MonetaryAmount;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Getter
public class VariableMonthlyTransactions {
    private UUID id;
    private String name;
    private Category category;
    private List<Transaction> referenceTransactions; //alle mit dieser Categorie
    private double averageAmount;
    private Type type;

    public double calculateAmountAsAverage(){
        //Calculates the Weighted Moving Average
        double sum = 0.0;
        if(referenceTransactions.isEmpty())
            return sum;
        //sort the list
        List<Transaction> sortedByDate = referenceTransactions.stream().sorted(Comparator.comparing(Transaction::getDate)).toList();
        double wma = 0.0;
        ArrayList<Double> monthlyAvg = new ArrayList<>(); //list of the monthly averages
        for(int monthIndex = 0; monthIndex <= monthsAgo(sortedByDate.get(sortedByDate.size()-1).getDate()); monthIndex++){
            int transactionInMonth = 0;
            double monthSum = 0.0;
            for(Transaction transaction : referenceTransactions){
                if (transaction.getMonetaryAmount().getAmount() != null &&
                        monthsAgo(transaction.getDate())==monthIndex){//might be somewhat slow
                        monthSum += transaction.getMonetaryAmount().getAmount();
                        transactionInMonth++;
                    }
            }
            if(transactionInMonth != 0)
                monthlyAvg.add(monthSum / transactionInMonth);
        }
        for (int i = 0; i < monthlyAvg.size(); i++) {
            wma += monthlyAvg.get(i)*(monthlyAvg.size()-i);
        }
        wma = wma*2/(monthlyAvg.size()*(monthlyAvg.size()+1));
        this.averageAmount = wma;
        return wma;
    }

    private int monthsAgo(Date date){
        return Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getMonths();
    }
}
