package com.example.Analytics.budgetFunctionality.logic.model;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateConverter {
    public LocalDate dateToLocalDate(Date date){
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Instant localDateToInstant(LocalDate localDate){
        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    }
}
