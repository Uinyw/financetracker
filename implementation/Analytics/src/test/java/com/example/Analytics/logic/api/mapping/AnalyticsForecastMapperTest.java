package com.example.Analytics.logic.api.mapping;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.ForecastMapper;
import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.logic.model.forecast.Forecast;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.CategoryDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.NutritionDto;
import org.openapitools.client.model.ProductDto;
import org.openapitools.model.ForecastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AnalyticsForecastMapperTest extends IntegrationTestBase {
    @Autowired
    private ForecastMapper forecastMapper;

    @Test
    void givenNutritionDto_whenMap_thenProductExists(){
        LocalDate localDate = LocalDate.now();
        UUID uuid = UUID.randomUUID();
        double amount = 5.0;

        HashMap<UUID, MonetaryAmount> forecastEntryList = new HashMap<>();
        forecastEntryList.put(uuid, new MonetaryAmount(amount));

        Forecast forecast = createForecast(localDate, forecastEntryList);
        ForecastDto forecastDto = forecastMapper.forecastToDto(forecast);

        assertThat(localDate.toString(), is(forecastDto.getDate()));
        assertThat(1, is(forecastDto.getPlan().size()));
        assertThat(uuid, is(forecastDto.getPlan().get(0).getBankAccount()));
        assertThat(amount, is(forecastDto.getPlan().get(0).getMonetaryAmount().getAmount()));
    }

    private Forecast createForecast(LocalDate localDate, HashMap<UUID, MonetaryAmount> forecastEntryList){
        return Forecast.builder()
                .date(localDate)
                .forecastEntriesList(forecastEntryList)
                .build();
    }
}
