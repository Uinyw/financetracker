package com.example.Analytics.logic.operations;

import com.example.Analytics.infrastructure.db.VariableMonthlyTransactionRepository;
import com.example.Analytics.logic.model.budgetModel.Transaction;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class VariableMonthlyTransactionService {
    @Autowired
    private final VariableMonthlyTransactionRepository variableMonthlyTransactionRepository;

    private void updateVariableMonthlyTransaction(final VariableMonthlyTransaction variableMonthlyTransaction) {
        deleteVariableMonthlyTransaction(variableMonthlyTransaction.getId().toString());
        saveVariableMonthlyTransaction(variableMonthlyTransaction);
    }

    private void deleteVariableMonthlyTransaction(final String variableMonthlyTransactionId) {
        variableMonthlyTransactionRepository.deleteById(UUID.fromString(variableMonthlyTransactionId));
    }

    private void saveVariableMonthlyTransaction(VariableMonthlyTransaction variableMonthlyTransaction){
        variableMonthlyTransactionRepository.save(variableMonthlyTransaction);
    }

    public List<VariableMonthlyTransaction> getVariableMonthlyTransaction() {
        return variableMonthlyTransactionRepository.findAll();
    }

    public List<VariableMonthlyTransaction> variableMonthlyTransactionChange(List<VariableMonthlyTransaction> variableMonthlyTransactionList){
        for(VariableMonthlyTransaction newTransaction : variableMonthlyTransactionList){
            boolean categoryAlreadyExists = false;
            for(VariableMonthlyTransaction existingTransaction: getVariableMonthlyTransaction()){
                if(sameCategory(newTransaction,existingTransaction) && !newTransaction.getReferenceTransactions().isEmpty()){
                    newTransaction.getReferenceTransactions().forEach(transaction -> {
                        deleteVariableMonthlyTransaction(existingTransaction.getId().toString());
                        appendTransaction(existingTransaction, transaction);
                        saveVariableMonthlyTransaction(existingTransaction);
                    });
                    //updateVariableMonthlyTransaction(existingTransaction);//TODO remove?
                    categoryAlreadyExists = true;
                }
            }
            if(!categoryAlreadyExists){
                System.out.println("no category");
                saveVariableMonthlyTransaction(createMonthEntry(newTransaction, newTransaction.getReferenceTransactions()));

            }
        }
        return getVariableMonthlyTransaction();
    }


    private VariableMonthlyTransaction appendTransaction(VariableMonthlyTransaction variableMonthlyTransaction,Transaction transaction){
        Transaction newTransaction = createNewTransactionEntry(transaction);
        variableMonthlyTransaction.appendTransaction(newTransaction);
        newTransaction.setVariableMonthlyTransaction(variableMonthlyTransaction);
        return variableMonthlyTransaction;
    }

    private Transaction createNewTransactionEntry(Transaction transaction){
        return Transaction.builder()
                .date(transaction.getDate())
                .referenceId(transaction.getReferenceId())
                .id(UUID.randomUUID())
                .amount(transaction.getAmount())
                .bankAccountSource(transaction.getBankAccountSource())
                .bankAccountTarget(transaction.getBankAccountTarget())
                .type(transaction.getType())
                .build();
    }


    public boolean sameCategory(VariableMonthlyTransaction variableMonthlyTransaction1, VariableMonthlyTransaction variableMonthlyTransaction2){
        return variableMonthlyTransaction1.getCategory().getName().equals(variableMonthlyTransaction2.getCategory().getName());
    }
    public List<VariableMonthlyTransaction> updateMonthlyTransactions(List<VariableMonthlyTransaction> variableMonthlyTransactionList) {
        if(variableMonthlyTransactionList == null || variableMonthlyTransactionList.isEmpty())
            return new ArrayList<>();

        return variableMonthlyTransactionList.stream()
                .map(this::filterMonth)
                .filter(Objects::nonNull)
                .toList();
    }

    private VariableMonthlyTransaction filterMonth(VariableMonthlyTransaction variableMonthlyTransaction) {
        List<Transaction> matchedTransactions = variableMonthlyTransaction.getReferenceTransactions().stream()
                .filter(t -> isWithinCurrentMonth(t.getDate()))
                .collect(Collectors.toList());

        return matchedTransactions.isEmpty() ? null : createMonthEntry(variableMonthlyTransaction, matchedTransactions);
    }

    private VariableMonthlyTransaction createMonthEntry(VariableMonthlyTransaction variableMonthlyTransaction, List<Transaction> matchedTransactions) {
        List<Transaction> newEntries = matchedTransactions.stream()
                .map(t -> Transaction.builder()
                        .id(UUID.randomUUID())
                        .referenceId(t.getReferenceId())
                        .date(t.getDate())
                        .type(t.getType())
                        .amount(t.getAmount())
                        .bankAccountSource(t.getBankAccountSource())
                        .bankAccountTarget(t.getBankAccountTarget())
                        .build())
                .toList();

        VariableMonthlyTransaction var = VariableMonthlyTransaction.builder()
                .id(UUID.randomUUID())
                .name(variableMonthlyTransaction.getName())
                .referenceTransactions(newEntries)
                .category(variableMonthlyTransaction.getCategory())
                .build();

        var.getReferenceTransactions().forEach(entry -> entry.setVariableMonthlyTransaction(var));
        return var;
    }

    public static boolean isWithinCurrentMonth(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return date.getYear() == currentDate.getYear() && date.getMonth() == currentDate.getMonth();
    }
}
