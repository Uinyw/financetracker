package com.example.Analytics.logic.operations;

import com.example.Analytics.logic.model.forecast.Forecast;
import com.example.Analytics.logic.model.forecast.ForecastEntities;
import com.example.Analytics.logic.model.forecast.RecurringSavingsGoal;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastService {

    private List<RecurringSavingsGoal> recurringSavingsGoalList;

    public Forecast createForecast(LocalDate date){
        List<ForecastEntities> forecastEntitiesList = new ArrayList<>();

        int months = getMonthDifference(date);
        for (int i = 1; i < months; i++) {
            ForecastEntities entity = createForecastForTimeFrame(LocalDate.now().plusMonths(i), date);
            forecastEntitiesList.add(entity);
        }
        ForecastEntities entity = createForecastForTimeFrame(LocalDate.now().plusMonths(months), date);
        forecastEntitiesList.add(entity);

        return new Forecast(forecastEntitiesList);
    }

    private int getMonthDifference(LocalDate date){
        LocalDate today = LocalDate.now();
        int yearsAgo = date.getYear()-today.getYear();
        int monthsAgo = date.getMonth().getValue()-today.getMonth().getValue();

        return yearsAgo*12 + monthsAgo;
    }

    private ForecastEntities createForecastForTimeFrame(LocalDate oldDate, LocalDate newDate){

        return null;
    }
}
