package com.example.Analytics.logic.model.productModel;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Duration {
    private final LocalDate startTime;
    private final LocalDate endTime;

    public Duration(final String startTime, final String endTime) {
        this.startTime = LocalDate.parse(startTime);
        this.endTime = LocalDate.parse(endTime);
    }
}
