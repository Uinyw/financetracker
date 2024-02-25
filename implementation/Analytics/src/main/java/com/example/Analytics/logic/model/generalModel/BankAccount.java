package com.example.Analytics.logic.model.generalModel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class BankAccount {
    @Id
    private UUID id;
    @Embedded
    private MonetaryAmount balance;

}
