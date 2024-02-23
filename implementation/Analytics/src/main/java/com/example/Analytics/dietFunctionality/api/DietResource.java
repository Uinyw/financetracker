package com.example.Analytics.dietFunctionality.api;

import com.example.Analytics.dietFunctionality.logic.model.Diet;
import com.example.Analytics.dietFunctionality.logic.model.Duration;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.NutritionApi;
import org.openapitools.model.DurationDto;
import org.openapitools.model.NutritionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DietResource implements NutritionApi {

    private final Diet diet;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<NutritionDto> nutritionGet(final DurationDto duration) {
        final var nutrition = diet.getNutritionForDuration(new Duration(duration.getStartTime(), duration.getEndTime()));
        var x = 0;
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
