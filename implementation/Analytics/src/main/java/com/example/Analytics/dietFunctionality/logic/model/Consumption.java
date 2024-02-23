package com.example.Analytics.dietFunctionality.logic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class Consumption {
    private LocalDate date;
    private double amount;
}
