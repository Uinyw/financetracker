package com.financetracker.product.api.mapping;

import com.financetracker.product.logic.model.*;
import org.openapitools.model.CategoryDto;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.NutritionDto;
import org.openapitools.model.ProductDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product mapProductDtoToModel(final ProductDto product) {
        return Product.with()
                .id(product.getId().toString())
                .name(product.getName())
                .description(product.getDescription())
                .price(mapMonetaryAmountDtoToModel(product.getPrice()))
                .category(mapCategoryDtoToModel(product.getCategory()))
                .labels(mapLabelDtoToModel(product.getLabels()))
                .build();
    }

    public ProductDto mapProductModelToDto(final Product product) {
        return ProductDto.builder()
                .id(UUID.fromString(product.getId()))
                .name(product.getName())
                .description(product.getDescription())
                .price(mapMonetaryAmountModelToDto(product.getPrice()))
                .category(mapCategoryModelToDto(product.getCategory()))
                .labels(mapLabelModelToDto(product.getLabels()))
                .nutrition(mapNutritionModelToDto(product.getNutrition()))
                .build();
    }

    private NutritionDto mapNutritionModelToDto(final Nutrition nutrition) {
        return NutritionDto.builder()
                .servingSize(nutrition.servingSize())
                .calories(nutrition.calories())
                .fat(nutrition.fat())
                .carbohydrates(nutrition.carbohydrates())
                .protein(nutrition.protein())
                .sugar(nutrition.sugar())
                .build();
    }

    private CategoryDto mapCategoryModelToDto(final Category category) {
        return switch (category) {
            case FOOD -> CategoryDto.FOOD;
            case DRINKS -> CategoryDto.DRINKS;
            case HOUSEHOLD -> CategoryDto.HOUSEHOLD;
            case ESSENTIALS -> CategoryDto.ESSENTIALS;
            case PET_SUPPLIES -> CategoryDto.PET_SUPPLIES;
        };
    }

    private Category mapCategoryDtoToModel(final CategoryDto category) {
        return switch (category) {
            case FOOD -> Category.FOOD;
            case DRINKS -> Category.DRINKS;
            case HOUSEHOLD -> Category.HOUSEHOLD;
            case ESSENTIALS -> Category.ESSENTIALS;
            case PET_SUPPLIES -> Category.PET_SUPPLIES;
        };
    }

    private MonetaryAmountDto mapMonetaryAmountModelToDto(final MonetaryAmount amount) {
        return MonetaryAmountDto.builder()
                .amount(amount != null && amount.amount() != null ? amount.amount().doubleValue() : null)
                .build();
    }

    private MonetaryAmount mapMonetaryAmountDtoToModel(final MonetaryAmountDto amountDto) {
        return new MonetaryAmount(BigDecimal.valueOf(amountDto.getAmount()));
    }

    private List<String> mapLabelModelToDto(final Set<Label> labels) {
        return labels.stream().map(Label::name).toList();
    }

    private Set<Label> mapLabelDtoToModel(final List<String> labels) {
        if (labels == null) {
            return Collections.emptySet();
        }

        return labels.stream()
                .map(Label::new)
                .collect(Collectors.toSet());
    }
}
