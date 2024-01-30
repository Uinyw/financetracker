package com.financetracker.product.logic.model;

import com.financetracker.product.infrastructure.db.converter.LabelSetConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder(builderMethodName = "with")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "product")
@Entity
public class Product {

    @Id
    private String id;
    private String name;
    private String description;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price", precision = 27, scale = 6))
    private MonetaryAmount price;

    private Category category;

    @Setter
    @Embedded
    @AttributeOverride(name = "servingSize", column = @Column(name = "nutrition_serving_size", precision = 27, scale = 6))
    @AttributeOverride(name = "calories", column = @Column(name = "nutrition_calories", precision = 27, scale = 6))
    @AttributeOverride(name = "carbohydrates", column = @Column(name = "nutrition_carbohydrates", precision = 27, scale = 6))
    @AttributeOverride(name = "protein", column = @Column(name = "nutrition_protein", precision = 27, scale = 6))
    @AttributeOverride(name = "fat", column = @Column(name = "nutrition_fat", precision = 27, scale = 6))
    @AttributeOverride(name = "sugar", column = @Column(name = "nutrition_sugar", precision = 27, scale = 6))
    private Nutrition nutrition;

    @Convert(converter = LabelSetConverter.class)
    @Column(name = "labels", nullable = false)
    protected Set<Label> labels = new HashSet<>();

    public boolean nutritionRequested() {
        return category.equals(Category.FOOD) || category.equals(Category.DRINKS);
    }
}
