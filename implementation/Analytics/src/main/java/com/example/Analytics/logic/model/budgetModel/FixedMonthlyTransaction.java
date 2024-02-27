package com.example.Analytics.logic.model.budgetModel;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FixedMonthlyTransaction")
@Entity
public class FixedMonthlyTransaction {
    @Id
    private UUID id;
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "category_name"))
    private Category category;
    private String name;
    @OneToMany(mappedBy = "fixedTransaction", cascade = CascadeType.ALL)
    private List<Transaction> transaction;
    private Periodicity periodicity;
}
