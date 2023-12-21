package com.financetracker.transaction.logic.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "one_time_transaction")
@Entity
public class OneTimeTransaction extends Transaction implements Transferable {

    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount", precision = 27, scale = 6))
    private MonetaryAmount amount;

    @Builder(builderMethodName = "with")
    public OneTimeTransaction(final String id,
                              final String name,
                              final String description,
                              final Type type,
                              final Set<Label> labels,
                              final Transfer transfer,
                              final LocalDate date,
                              final MonetaryAmount amount) {
        super(id, name, description, type, labels, transfer);
        this.date = date;
        this.amount = amount;
    }
}
