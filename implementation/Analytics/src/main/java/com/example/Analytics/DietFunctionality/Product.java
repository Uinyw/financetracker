package com.example.Analytics.DietFunctionality;

import lombok.Builder;
import org.openapitools.client.model.ProductDto;

import java.util.UUID;

@Builder
public class Product {
    private UUID id;
    private String name;
    private int size;
    private Consumption consumption;
    private Nutrition nutrition;
}
