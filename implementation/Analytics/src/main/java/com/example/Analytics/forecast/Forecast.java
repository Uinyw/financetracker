package com.example.Analytics.forecast;

import org.openapitools.client.model.MonetaryAmount;

import java.util.Date;
import java.util.Map;

public class Forecast {
    private Date date;
    private Map<BankAccount, MonetaryAmount> balances;
}
