package com.example.Analytics.api;

import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.logic.operations.DietService;
import com.example.Analytics.logic.model.productModel.Duration;
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

    private final DietService dietService;
    private final ProductMapper productMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<NutritionDto> nutritionPost(final DurationDto durationDto) {
        final var nutrition = dietService.getNutritionForDuration(new Duration(durationDto.getStartTime(), durationDto.getEndTime()));
        NutritionDto nutritionDto = productMapper.nutritionDtoFromNutrition(nutrition);
        return new ResponseEntity<>(nutritionDto, HttpStatus.OK);
    }

}
