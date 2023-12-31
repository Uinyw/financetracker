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
    @AttributeOverride(name = "externalSourceId", column = @Column(name = "transfer_external_source_id"))
    @AttributeOverride(name = "sourceBankAccountId", column = @Column(name = "transfer_source_bank_account_id"))
    @AttributeOverride(name = "targetBankAccountId", column = @Column(name = "transfer_target_bank_account_id"))
    @AttributeOverride(name = "externalTargetId", column = @Column(name = "transfer_external_target_id"))
    protected Transfer transfer;

}
