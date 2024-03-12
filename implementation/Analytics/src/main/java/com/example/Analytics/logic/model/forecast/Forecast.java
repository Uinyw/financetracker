package com.example.Analytics.logic.model.forecast;

import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Forecast {
    private LocalDate date;
    private HashMap<UUID, MonetaryAmount> forecastEntriesList;
}
