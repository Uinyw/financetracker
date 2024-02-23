package com.example.Analytics.dietFunctionality.logic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@Table(name = "product")
@Entity
public class Product {
    @Id
    private final UUID id;
    private String name;
    private double size;
    private Consumption consumption;
    private Nutrition nutrition;
}
