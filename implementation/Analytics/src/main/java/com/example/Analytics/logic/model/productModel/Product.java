package com.example.Analytics.logic.model.productModel;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Entity
public class Product {
    @Id
    private UUID id;
    private String name;
    private double size;
    @Embedded
    private Consumption consumption;
    @Embedded
    private Nutrition nutrition;
}
