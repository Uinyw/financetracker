package com.example.Analytics.dietFunctionality.api;

import com.example.Analytics.dietFunctionality.api.mapping.ProductMapper;
import com.example.Analytics.dietFunctionality.logic.model.Duration;
import com.example.Analytics.dietFunctionality.logic.operations.ProductService;
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
    //todo implements dietApi
    private final ProductService productService;
    private final ProductMapper productMapper;


    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<NutritionDto> nutritionPost(final DurationDto duration) {

        productService.getDiet(Duration.builder().startTime(duration.getStartTime()).endTime(duration.getEndTime()).build());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
