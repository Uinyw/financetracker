package com.example.Analytics.api;

import com.example.Analytics.api.mapping.ForecastMapper;
import com.example.Analytics.logic.model.forecast.Forecast;
import com.example.Analytics.logic.operations.ForecastService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ForecastApi;
import org.openapitools.model.ForecastDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class ForecastResource implements ForecastApi {

    private final ForecastService forecastService;
    private final ForecastMapper forecastMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<ForecastDto> forecastGet(String date) {
        final Forecast forecast = forecastService.createForecast(LocalDate.parse(date));
        return new ResponseEntity<>(forecastMapper.forecastToDto(forecast), HttpStatus.OK);
    }

}
