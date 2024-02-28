package com.example.Analytics.logic.model.forecast;

import com.example.Analytics.logic.model.generalModel.BankAccount;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ForecastEntry {
    private UUID bankAccountId;
    private MonetaryAmount monetaryAmount;
}
