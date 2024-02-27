package com.example.Analytics.logic.model.budgetModel;

import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    private UUID id;
    private UUID referenceId;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "variable_monthly_transaction_id")
    private VariableMonthlyTransaction variableMonthlyTransaction;

    @ManyToOne
    @JoinColumn(name = "fixed_transaction_id")
    private FixedTransaction fixedTransaction;

    private UUID bankAccountSource;
    private UUID bankAccountTarget;

    @Embedded
    private MonetaryAmount amount;

    private TransactionType type;

}
