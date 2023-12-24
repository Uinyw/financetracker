package com.financetracker.transaction.logic.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "recurring_transaction")
@Entity
public class RecurringTransaction extends Transaction {

    private Date startDate;
    private Periodicity periodicity;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount", precision = 27, scale = 6))
    private MonetaryAmount fixedAmount;

    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TransactionRecord> transactionRecords = new HashSet<>();

    @Builder(builderMethodName = "with")
    public RecurringTransaction(final String id,
                                final String name,
                                final String description,
                                final Type type,
                                final Set<Label> labels,
                                final Transfer transfer,
                                final Date startDate,
                                final Periodicity periodicity,
                                final MonetaryAmount fixedAmount,
                                final Set<TransactionRecord> transactionRecords) {
        super(id, name, description, type, labels, transfer);
        this.startDate = startDate;
        this.periodicity = periodicity;
        this.fixedAmount = fixedAmount;
        this.transactionRecords = transactionRecords;
    }
}
