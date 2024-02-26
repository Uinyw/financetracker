package com.example.Analytics.api;

import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.operations.DietService;
import com.example.Analytics.logic.operations.ReportService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.NutritionApi;
import org.openapitools.api.ReportApi;
import org.openapitools.model.DurationDto;
import org.openapitools.model.NutritionDto;
import org.openapitools.model.ReportDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReportResource implements ReportApi {

    private final ReportService reportService;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<ReportDto> reportGet() {
        reportService.generateReport();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
