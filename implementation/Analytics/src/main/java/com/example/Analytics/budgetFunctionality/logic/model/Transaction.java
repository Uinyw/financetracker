package com.example.Analytics.budgetFunctionality.logic.model;

import com.example.Analytics.forecast.BankAccount;
import com.example.Analytics.Label;
import com.example.Analytics.LabelSetConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.client.model.MonetaryAmount;

import java.util.*;

@Getter
@Setter
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    private UUID id;
    @Column(name = "date")
    private Date date;
    @Column(name = "name")
    private String name;
    @Column(name = "transactionType")
    private TransactionType transactionType;
    @Column(name = "periodicity")
    private Periodicity periodicity;

    @ManyToOne
    private BankAccount sourceBankAccount;
    @ManyToOne
    private BankAccount targetBankAccount;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "monetaryAmount", precision = 27, scale = 6))
    private MonetaryAmount monetaryAmount;

    @Convert(converter = LabelSetConverter.class)
    @Column(name = "labels", nullable = false)
    private Set<Label> labels = new HashSet<>();

}
