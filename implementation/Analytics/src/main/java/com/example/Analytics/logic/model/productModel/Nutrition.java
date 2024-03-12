package com.example.Analytics.logic.model.productModel;

import jakarta.persistence.Embeddable;
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
