package com.example.Analytics.DietFunctionality;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.client.model.ProductDto;

import java.util.UUID;

@Builder
@Getter
@Setter
public class Product {
    private UUID id;
    private String name;
    private double size;
    private Consumption consumption;
    private Nutrition nutrition;
}
