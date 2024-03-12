package com.example.Analytics.logic.model.budgetModel;


import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FixedMonthlyTransaction")
@Entity
public class FixedTransaction {
    @Id
    private UUID id;
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "category_name"))
    private Category category;
    private String name;
    @OneToMany(mappedBy = "fixedTransaction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> referenceTransactions;
    private Periodicity periodicity;

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

    public MonetaryAmount calculateAverageAmount(){
        List<Transaction> lastYearTransactions = referenceTransactions.stream().filter(transaction -> transaction.getDate().isAfter(LocalDate.now().minusYears(1))).toList();
        double monetaryAmount = lastYearTransactions.stream().map(transaction -> transaction.getAmount().getAmount()/(double)this.getPeriodicity().getMonths()).mapToDouble(Double::doubleValue).sum();
        double monetaryAmountRounded = Math.round(monetaryAmount*100.0)/100.0;
        return new MonetaryAmount(monetaryAmountRounded);
    }

}
