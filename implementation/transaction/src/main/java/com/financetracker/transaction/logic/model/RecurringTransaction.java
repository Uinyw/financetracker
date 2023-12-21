package com.financetracker.transaction.logic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Table(name = "recurring_transaction")
@Entity
public class RecurringTransaction extends Transaction {

    private Date startDate;
    private Periodicity periodicity;

    @Builder(builderMethodName = "with")
    public RecurringTransaction(final String id,
                                final String name,
                                final String description,
                                final Type type,
                                final Set<Label> categories,
                                final Transfer transfer,
                                final Date startDate,
                                final Periodicity periodicity) {
        super(id, name, description, type, categories, transfer);
        this.startDate = startDate;
        this.periodicity = periodicity;
    }
}
