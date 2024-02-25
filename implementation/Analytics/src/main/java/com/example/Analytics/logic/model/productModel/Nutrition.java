package com.example.Analytics.logic.model.productModel;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Nutrition {
    private double servingSize;
    private double calories;
    private double carbohydrates;
    private double protein;
    private double fat;
    private double sugar;

}
