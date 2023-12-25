package com.financetracker.transaction.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @Setter
    private TransferStatus transferStatus;
}
