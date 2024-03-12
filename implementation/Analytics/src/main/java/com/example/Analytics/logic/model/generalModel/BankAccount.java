package com.example.Analytics.logic.model.generalModel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    private UUID id;
    @Embedded
    private MonetaryAmount balance;

}
