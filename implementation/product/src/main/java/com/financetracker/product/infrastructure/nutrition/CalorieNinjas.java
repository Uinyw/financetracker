package com.financetracker.product.infrastructure.nutrition;

import com.financetracker.product.infrastructure.nutrition.mapping.NutritionMapper;
import com.financetracker.product.infrastructure.nutrition.model.NutritionInformation;
import com.financetracker.product.logic.model.Nutrition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CalorieNinjas implements NutritionProvider {

    private static final String URI = "https://api.calorieninjas.com/v1/nutrition?query=";
    private static final String API_KEY = "YRYt3S1YpvnjvIdaNm+h7A==l0uptsed0OgIttpP";

    private final NutritionMapper nutritionMapper;

    @Override
    public Nutrition getNutritionForProduct(String productName) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Api-Key", API_KEY);

        final NutritionInformation nutritionInformation = restTemplate.exchange(URI + productName, HttpMethod.GET, new HttpEntity<>("parameters", headers), NutritionInformation.class).getBody();
        return Optional.ofNullable(nutritionInformation).map(nutritionMapper::mapNutritionInformationToNutritionModel).orElse(null);
    }
}
