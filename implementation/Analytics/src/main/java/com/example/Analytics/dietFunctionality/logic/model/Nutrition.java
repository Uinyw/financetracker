package com.example.Analytics.dietFunctionality.logic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Nutrition {
    private double servingSize;
    private double calories;
    private double carbohydrates;
    private double protein;
    private double fat;
    private double sugar;

}
