package com.example.Analytics.api;

import com.example.Analytics.api.mapping.ForecastMapper;
import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.logic.model.forecast.Forecast;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.operations.DietService;
import com.example.Analytics.logic.operations.ForecastService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ForecastmonthsApi;
import org.openapitools.api.NutritionApi;
import org.openapitools.model.DurationDto;
import org.openapitools.model.ForecastDto;
import org.openapitools.model.NutritionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class ForecastResorce implements ForecastmonthsApi {

    private final ForecastService forecastService;
    private final ForecastMapper forecastMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<ForecastDto> forecastmonthsGet(Integer monthlyAmount) {
        final Forecast forecast = forecastService.createForecast(LocalDate.now().plusMonths(monthlyAmount));
        return new ResponseEntity<>(forecastMapper.forecastToDto(forecast), HttpStatus.OK);
    }

}
