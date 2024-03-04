package com.example.Analytics.logic.operations;

import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.forecast.Forecast;
import com.example.Analytics.logic.model.forecast.ForecastEntities;
import com.example.Analytics.logic.model.forecast.RecurringSavingsGoal;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import com.example.Analytics.logic.model.productModel.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class ForecastService {

    private List<RecurringSavingsGoal> recurringSavingsGoalList;
    @Autowired
    private BudgetService budgetService;

    public Forecast createForecast(LocalDate date){
        HashMap<UUID, MonetaryAmount> forecastEntriesList = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        List<BudgetElement> budgetElementList = budgetService.spendingForEachCategory(FilterElement.builder()
                .bankAccountList(new ArrayList<>())
                .categoryList(new ArrayList<>())
                .duration(new Duration(localDate.minusMonths(1).toString(),localDate.toString())).build()).getBudgetElementList();

        for(BudgetElement budgetElement : budgetElementList){
            forecastEntriesList.put(UUID.randomUUID(), budgetElement.getMonetaryAmount());
        }

        return Forecast.builder()
                .date(date)
                .forecastEntriesList(forecastEntriesList)
                .build();
    }
}
