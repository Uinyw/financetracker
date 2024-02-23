package com.example.Analytics.forecast;

import jakarta.persistence.*;
import org.openapitools.client.model.MonetaryAmount;

import java.util.UUID;

@Table(name = "bank_account")
@Entity
public class BankAccount {
    @Id
    private UUID id;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "balance", precision = 27, scale = 6))
    private MonetaryAmount balance;
}
