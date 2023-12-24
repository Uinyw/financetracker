package com.financetracker.transaction.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_record")
@Entity
public class TransactionRecord implements Transferable {

    @Id
    private String id;

    @Column(name = "transaction_id")
    private String transactionId;

    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount", precision = 27, scale = 6))
    private MonetaryAmount amount;
}
