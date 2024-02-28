package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.forecast.Forecast;
import com.example.Analytics.logic.model.forecast.ForecastEntities;
import com.example.Analytics.logic.model.forecast.ForecastEntry;
import org.openapitools.model.ForecastDto;
import org.openapitools.model.ForecastElement;
import org.openapitools.model.MonetaryAmountDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ForecastMapper {
    /*
    frorcast DTO
    months = anzahl monate
    date = jetzt <- pretty much useless
    Forecast Element = category+monat -> geld/bankAccount
     */

    public ForecastDto forecastToDto(Forecast forecast){
        ForecastDto forecastDto = ForecastDto.builder()
                .date(LocalDate.now().toString())
                .months(0)
                .plan(getHashMapDto(forecast.getForecastEntitiesList()))
                .build();

        if(forecast.getForecastEntitiesList() != null){
            forecastDto.setMonths(forecast.getForecastEntitiesList().size());
        }
        return forecastDto;
    }

    private HashMap<String, ForecastElement> getHashMapDto(List<ForecastEntities> forecastEntitiesList){
        HashMap<String, ForecastElement> newHasmapDto = new HashMap<>();
        if(forecastEntitiesList==null)
            return newHasmapDto;

        for(ForecastEntities forecastEntity : forecastEntitiesList){
            String date = forecastEntity.getDate().toString();
            forecastEntity.getForecastEntriesList().forEach(
                    (category, forecastEntry) -> {
                        String newKey = date + " [" + category + "]";
                        newHasmapDto.put(newKey, forecastEntriesToDto(forecastEntry));
                    }
            );
        }

        return newHasmapDto;
    }

    private ForecastElement forecastEntriesToDto(ForecastEntry forecastEntry){
        ForecastElement newForecastElement = ForecastElement.builder()
                .bankAccount(forecastEntry.getBankAccountId())
                .monetaryAmount(new MonetaryAmountDto(forecastEntry.getMonetaryAmount().getAmount()))
                .build();
        return newForecastElement;
    }
}
