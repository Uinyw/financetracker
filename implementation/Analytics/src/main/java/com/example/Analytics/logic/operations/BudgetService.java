package com.example.Analytics.logic.operations;

import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import lombok.RequiredArgsConstructor;
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


    public void variableMonthlyTransactionChange(List<VariableMonthlyTransaction> variableMonthlyTransactionList){
        this.history = variableMonthlyTransactionService.variableMonthlyTransactionChange(variableMonthlyTransactionList);
        //this.currentMonth = variableMonthlyTransactionService.updateMonthlyTransactions(this.history);
        System.out.println("----------history------------");
        debugPrint(history);
        System.out.println("----------currentMonth------------");
        //debugPrint(currentMonth);
        System.out.println("----------------------------------");
    }

    public void fixedMonthlyTransactionChange(){

    }

    private void calculateSavingPerCategory(){

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

}
