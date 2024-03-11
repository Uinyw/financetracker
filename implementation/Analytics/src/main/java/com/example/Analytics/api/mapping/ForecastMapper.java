package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.forecast.Forecast;
import org.openapitools.model.ForecastDto;
import org.openapitools.model.ForecastElementDto;
import org.openapitools.model.MonetaryAmountDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ForecastMapper {
    public ForecastDto forecastToDto(Forecast forecast){
        List<ForecastElementDto> forecastElementDtos = forecast.getForecastEntriesList().entrySet().stream().map(set->
            new ForecastElementDto(set.getKey(), new MonetaryAmountDto(set.getValue().getAmount()))
        ).toList();
        return ForecastDto.builder()
                .date(forecast.getDate().toString())
                .plan(forecastElementDtos)
                .build();
    }
}
