package com.example.Analytics.logic.model.forecast;

import com.example.Analytics.logic.model.budgetModel.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
public class Forecast {
    private List<ForecastEntities> forecastEntitiesList;
}
