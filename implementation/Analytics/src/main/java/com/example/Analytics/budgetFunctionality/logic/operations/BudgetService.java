package com.example.Analytics.budgetFunctionality.logic.operations;

import com.example.Analytics.budgetFunctionality.logic.model.Budget;
import com.example.Analytics.budgetFunctionality.logic.model.FixedMonthlyTransaction;
import com.example.Analytics.budgetFunctionality.infrastructure.db.FixedMonthlyTransactionsRepository;
import com.example.Analytics.budgetFunctionality.infrastructure.db.VariableMonthlyTransactionsRepository;
import com.example.Analytics.budgetFunctionality.logic.model.VariableMonthlyTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BudgetService {

    private final VariableMonthlyTransactionsRepository variableMonthlyTransactionsRepository;
    private final FixedMonthlyTransactionsRepository fixedMonthlyTransactionsRepository;
    private final Budget budget;


    public List<VariableMonthlyTransaction> getVariableMonthlyTransactions(){
        return variableMonthlyTransactionsRepository.findAll();
    }


    public Optional<VariableMonthlyTransaction> getVariableMonthlyTransaction(final String variableMonthlyTransactionsId) {
        return variableMonthlyTransactionsRepository.findById(variableMonthlyTransactionsId);
    }

    public void createVariableMonthlyTransactions(final VariableMonthlyTransaction variableMonthlyTransaction) {
        variableMonthlyTransactionsRepository.save(variableMonthlyTransaction);
    }

    public void updateVariableMonthlyTransactions(final VariableMonthlyTransaction variableMonthlyTransaction) {
        variableMonthlyTransactionsRepository.save(variableMonthlyTransaction);
    }

    public void deleteVariableMonthlyTransactions(final String variableMonthlyTransactionId) {
        variableMonthlyTransactionsRepository.deleteById(variableMonthlyTransactionId);
    }



    public List<FixedMonthlyTransaction> getFixedMonthlyTransactions(){
        return fixedMonthlyTransactionsRepository.findAll();
    }

    public Optional<FixedMonthlyTransaction> getFixedMonthlyTransactions(final String fixedMonthlyTransactionsId) {
        return fixedMonthlyTransactionsRepository.findById(fixedMonthlyTransactionsId);
    }

    public void createFixedMonthlyTransactions(final FixedMonthlyTransaction fixedMonthlyTransactions) {
        fixedMonthlyTransactionsRepository.save(fixedMonthlyTransactions);
    }

    public void updateFixedMonthlyTransactions(final FixedMonthlyTransaction fixedMonthlyTransactions) {
        fixedMonthlyTransactionsRepository.save(fixedMonthlyTransactions);
    }

    public void deleteFixedMonthlyTransactions(final String fixedMonthlyTransactionsId) {
        fixedMonthlyTransactionsRepository.deleteById(fixedMonthlyTransactionsId);
    }



    public void budgetUpdate(){
        //TODO muss verwendet werden
        List<VariableMonthlyTransaction> variableMonthlyTransactions = getVariableMonthlyTransactions();
        List<FixedMonthlyTransaction> fixedMonthlyTransactions = getFixedMonthlyTransactions();
        this.budget.budgetUpdate(variableMonthlyTransactions, fixedMonthlyTransactions);
    }


    public void receivedOneTimeTransactionUpdate(){
        //TODO update transaction
        /*
        switch (payload){
            case "CREATE":

                break;
            case "UPDATE":

                break;
            case "DELETE":

                break;
        }*/
    }

}
