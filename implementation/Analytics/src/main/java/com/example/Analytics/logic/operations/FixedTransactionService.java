package com.example.Analytics.logic.operations;

import com.example.Analytics.infrastructure.db.FixedTransactionRepository;
import com.example.Analytics.logic.model.budgetModel.FixedTransaction;
import com.example.Analytics.logic.model.budgetModel.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FixedTransactionService {

    @Autowired
    private final FixedTransactionRepository fixedTransactionRepository;

    public List<FixedTransaction> fixedTransactionUpdate(List<FixedTransaction> fixedTransactionList) {
        fixedTransactionDelete(fixedTransactionList);
        return fixedTransactionCreate(fixedTransactionList);
    }

    private void deleteFixedTransaction(final String fixedTransactionId) {
        fixedTransactionRepository.deleteById(UUID.fromString(fixedTransactionId));
    }

    private void saveFixedTransaction(FixedTransaction fixedTransaction){
        fixedTransactionRepository.save(fixedTransaction);
    }

    public List<FixedTransaction> getFixedTransaction() {
        return fixedTransactionRepository.findAll();
    }

    public List<FixedTransaction> fixedTransactionDelete(List<FixedTransaction> fixedTransactionList){
        List<UUID> uuidsToBeDeleted = new ArrayList<>();
        for(FixedTransaction newTransaction : fixedTransactionList){
            for(Transaction transactionToBeDeleted : newTransaction.getReferenceTransactions()){
                uuidsToBeDeleted.add(transactionToBeDeleted.getReferenceId());
            }
        }
        List<FixedTransaction> saveDelete=new ArrayList<>();
        for(UUID uuidToBeDeleted : uuidsToBeDeleted){
            for(FixedTransaction existingTransaction: getFixedTransaction()){
                for(Transaction transaction : existingTransaction.getReferenceTransactions()){
                    if(uuidToBeDeleted.equals(transaction.getReferenceId())){
                        saveDelete.add(existingTransaction);
                    }
                }
            }
        }
        for(UUID uuidToBeDeleted : uuidsToBeDeleted) {
            for (FixedTransaction hasChildToBeDeleted : saveDelete) {
                deleteFixedTransaction(hasChildToBeDeleted.getId().toString());
                hasChildToBeDeleted.getReferenceTransactions().removeIf(transaction -> transaction.getReferenceId().equals(uuidToBeDeleted));
                if(!hasChildToBeDeleted.getReferenceTransactions().isEmpty())
                    saveFixedTransaction(hasChildToBeDeleted);
            }
        }

        return getFixedTransaction();
    }

    public List<FixedTransaction> fixedTransactionCreate(List<FixedTransaction> fixedTransactions){
        for(FixedTransaction newTransaction : fixedTransactions){
            boolean categoryAlreadyExists = false;
            for(FixedTransaction existingTransaction: getFixedTransaction()){
                if(sameCategory(newTransaction,existingTransaction) && !newTransaction.getReferenceTransactions().isEmpty() && existingTransaction.getPeriodicity().name().equals(newTransaction.getPeriodicity().name())){
                    newTransaction.getReferenceTransactions().forEach(transaction -> {
                        deleteFixedTransaction(existingTransaction.getId().toString());
                        appendTransaction(existingTransaction, transaction);
                        saveFixedTransaction(existingTransaction);
                    });
                    categoryAlreadyExists = true;
                }
            }
            if(!categoryAlreadyExists){
                saveFixedTransaction(createMonthEntry(newTransaction, newTransaction.getReferenceTransactions()));

            }
        }
        return getFixedTransaction();
    }


    private FixedTransaction appendTransaction(FixedTransaction fixedTransaction,Transaction transaction){
        Transaction newTransaction = createNewTransactionEntry(transaction);
        fixedTransaction.appendTransaction(newTransaction);
        newTransaction.setFixedTransaction(fixedTransaction);
        return fixedTransaction;
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

    public boolean sameCategory(FixedTransaction fixedTransaction1, FixedTransaction fixedTransaction2){
        return fixedTransaction1.getCategory().getName().equals(fixedTransaction2.getCategory().getName());
    }

    private FixedTransaction createMonthEntry(FixedTransaction fixedTransaction, List<Transaction> matchedTransactions) {
        List<Transaction> newEntries = matchedTransactions.stream()
                .map(this::createNewTransactionEntry).toList();

        FixedTransaction var = FixedTransaction.builder()
                .id(UUID.randomUUID())
                .name(fixedTransaction.getName())
                .referenceTransactions(newEntries)
                .category(fixedTransaction.getCategory())
                .periodicity(fixedTransaction.getPeriodicity())
                .build();

        var.getReferenceTransactions().forEach(entry -> entry.setFixedTransaction(var));
        return var;
    }

}
