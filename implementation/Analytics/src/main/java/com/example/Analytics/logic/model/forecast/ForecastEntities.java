package com.example.Analytics.logic.model.forecast;

import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;

@Getter
public class ForecastEntities {
    private LocalDate date;
    private HashMap<String, ForecastEntry> forecastEntriesList;
}
