package com.example.Analytics.logic.model.budgetModel;

import com.example.Analytics.logic.model.generalModel.AmountMonthsAgo;
import com.example.Analytics.logic.model.generalModel.YearlyMonth;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VariableMonthlyTransaction")
@Entity
public class VariableMonthlyTransaction {
    @Id
    private UUID id;
    private String name; //TODO is the name even needed?
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "category_name"))
    private Category category;


    @OneToMany(mappedBy = "variableMonthlyTransaction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> referenceTransactions;

    public void appendTransaction(Transaction transaction){
        if(referenceTransactions == null){
            referenceTransactions = new ArrayList<>();
        }
        if(referenceIdAndDateMissmatch(transaction))
            referenceTransactions.add(transaction);
    }
    private boolean referenceIdAndDateMissmatch(Transaction transaction){
        if(referenceTransactions == null || referenceTransactions.isEmpty())
            return true;
        boolean missmatch = true;
        for(Transaction refTransaction : referenceTransactions){
            if(refTransaction.getReferenceId().equals(transaction.getReferenceId())
                    && refTransaction.getDate().isEqual(transaction.getDate()))
                missmatch = false;
        }
        return missmatch;
    }

    public double calculateAmountAsAverage(){
        List<List<Transaction>> monthlyTransactions = getMonthlyTransactions(referenceTransactions);
        List<AmountMonthsAgo> amountMonthsAgoList = monthlyTransactions.stream().map(transactionList ->
                new AmountMonthsAgo(getMonthsAgo(transactionList), getMoneySum(transactionList))
        ).toList();
        int maxTimeAmount = amountMonthsAgoList.stream()
                .mapToInt(AmountMonthsAgo::getMonthsAgo)
                .max()
                .orElse(0);
        int lowestTimeAmount = amountMonthsAgoList.stream()
                .mapToInt(AmountMonthsAgo::getMonthsAgo)
                .min()
                .orElse(0);
        //TODO ignore new month; first month is important -> lowest value = one month ago if several months
        double baseline = getLastMonthAmount(amountMonthsAgoList, lowestTimeAmount);
        for(AmountMonthsAgo amountMonthsAgo : amountMonthsAgoList){
            double weighting = getMonthWeighting(amountMonthsAgo.getMonthsAgo(), maxTimeAmount);
            double difference = (amountMonthsAgo.getAmount()-baseline);
            baseline += difference*weighting;
        }
        return baseline;
    }

    private double getMonthWeighting(int month, int maxValue){
        if(month == 0){
            return 0.0;
        }
        return (1.0)/(month+1.0);
    }

    private double getLastMonthAmount(List<AmountMonthsAgo> amountMonthsAgoList, int month){
        double amount = 0.0;
        for(AmountMonthsAgo amountMonthsAgo : amountMonthsAgoList){
            if(amountMonthsAgo.getMonthsAgo() == month)
                amount = amountMonthsAgo.getAmount();
        }
        return amount;
    }

    private int getMonthsAgo(List<Transaction> monthList){
        if(monthList.isEmpty())
            return 0;
        LocalDate dateOfTransaction = monthList.get(0).getDate();
        LocalDate today = LocalDate.now();
        int yearsAgo = today.getYear() - dateOfTransaction.getYear();
        int monthsAgo = today.getMonth().getValue() - dateOfTransaction.getMonth().getValue();

        return yearsAgo*12 + monthsAgo;
    }
    private double getMoneySum(List<Transaction> transactionList){
        double money = 0.0;
        for(Transaction transaction: transactionList){
            if(transaction.getType().equals(TransactionType.EXPENSE))
                money -= transaction.getAmount().getAmount();

            if(transaction.getType().equals(TransactionType.INCOME))
                money += transaction.getAmount().getAmount();
        }
        return money;
    }

    private List<List<Transaction>> getMonthlyTransactions(List<Transaction> transactionList){
        HashMap<String, List<Transaction>> monthList = new HashMap<>();

        for(Transaction transaction : transactionList){
            YearlyMonth key = new YearlyMonth(transaction.getDate().getMonth().getValue(), transaction.getDate().getYear());
            List<Transaction> transactions = new ArrayList<>();
            if(monthList.containsKey(key.getString())){
                transactions = monthList.get(key.getString());
            }
            transactions.add(transaction);
            monthList.put(key.getString(), transactions);
        }

        return new ArrayList<>(monthList.values());
    }


}
