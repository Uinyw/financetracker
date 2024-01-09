package com.financetracker.transaction.logic.model;

import com.financetracker.transaction.utils.UUIDGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "label")
@Entity
public class Label {
    @Id
    private String id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Getter
    private String name;

    public Label(final String transactionId, final String name) {
        this.id = UUIDGenerator.create();
        this.name = name;
    }

}
