package com.example.Analytics.api;

import com.example.Analytics.logic.operations.ReportService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ReportApi;
import org.openapitools.model.ReportFilterDto;
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
    public ResponseEntity<Void> reportGet(ReportFilterDto filter) {
        reportService.generateReport();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
