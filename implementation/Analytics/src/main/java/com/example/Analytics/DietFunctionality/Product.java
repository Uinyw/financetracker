package com.example.Analytics.DietFunctionality;

import lombok.Builder;
import lombok.Getter;
import org.openapitools.client.model.ProductDto;

import java.util.UUID;

@Builder
@Getter
public class Product {
    private UUID id;
    private String name;
    private int size;
    private Consumption consumption;
    private Nutrition nutrition;
}
