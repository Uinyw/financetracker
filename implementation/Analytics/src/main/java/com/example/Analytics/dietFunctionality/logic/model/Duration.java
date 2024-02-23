package com.example.Analytics.dietFunctionality.logic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Duration {
    LocalDate startTime;
    LocalDate endTime;
}
