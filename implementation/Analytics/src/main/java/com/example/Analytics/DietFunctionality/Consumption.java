package com.example.Analytics.DietFunctionality;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter
public class Consumption {
    private LocalDate date;
    private double amount;
}
