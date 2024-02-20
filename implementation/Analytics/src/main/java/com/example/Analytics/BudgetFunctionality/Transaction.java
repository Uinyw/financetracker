package com.example.Analytics.BudgetFunctionality;

import com.example.Analytics.BankAccount;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.client.model.MonetaryAmount;
import org.openapitools.model.Periodicity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private UUID id;
    private Date date;
    private String name;
    private TransactionType transactionType;
    private Periodicity periodicity;
    private MonetaryAmount monetaryAmount;
    private List<String> labels;
    private BankAccount sourceBankAccount;
    private BankAccount targetBankAccount;
}
