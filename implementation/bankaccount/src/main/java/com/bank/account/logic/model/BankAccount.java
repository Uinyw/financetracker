package com.bank.account.logic.model;

import com.bank.account.infrastructure.converter.LabelSetConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder(builderMethodName = "with")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "bank_account")
@Entity
public class BankAccount {

    @Id
    private String id;
    private String name;
    private String description;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "balance", precision = 27, scale = 6))
    private MonetaryAmount balance;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "disposition_limit", precision = 27, scale = 6))
    private MonetaryAmount dispositionLimit;

    @Convert(converter = LabelSetConverter.class)
    @Column(name = "labels", nullable = false)
    private Set<Label> labels = new HashSet<>();

}
