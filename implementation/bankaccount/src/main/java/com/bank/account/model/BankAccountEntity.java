package com.bank.account.model;

import java.util.List;
import java.util.UUID;
import org.openapitools.model.Transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "with")
@Entity
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "balance")
    private double balance;

    @Column(name = "dispoLimit")
    private double dispoLimit;

    @Column(name = "labels")
    private List<String> labels = null;
}
