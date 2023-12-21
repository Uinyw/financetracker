package com.financetracker.transaction.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    protected Set<Label> labels;

    @Embedded
    @AttributeOverride(name = "sourceId", column = @Column(name = "transfer_source_id"))
    @AttributeOverride(name = "targetBankAccountId", column = @Column(name = "transfer_target_bank_account_id"))
    protected Transfer transfer;

}
