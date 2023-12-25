package com.financetracker.transaction.logic.model;

import com.financetracker.transaction.infrastructure.db.converter.LabelSetConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Transaction {

    @Id
    protected String id;

    protected String name;

    protected String description;

    protected Type type;

    @Convert(converter = LabelSetConverter.class)
    @Column(name = "labels", nullable = false)
    protected Set<Label> labels = new HashSet<>();

    @Embedded
    @AttributeOverride(name = "sourceId", column = @Column(name = "transfer_source_id"))
    @AttributeOverride(name = "targetBankAccountId", column = @Column(name = "transfer_target_bank_account_id"))
    protected Transfer transfer;

}
