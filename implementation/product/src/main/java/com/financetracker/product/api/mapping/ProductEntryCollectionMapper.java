package com.financetracker.product.api.mapping;

import com.financetracker.product.logic.model.Category;
import com.financetracker.product.logic.model.ProductEntry;
import com.financetracker.product.logic.model.ProductEntryCollection;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.CategoryDto;
import org.openapitools.model.ProductEntryCollectionDto;
import org.openapitools.model.ProductEntryCollectionTypeDto;
import org.openapitools.model.ProductEntryDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductEntryCollectionMapper {

    private final ProductEntryMapper productEntryMapper;

    public ProductEntryCollectionDto mapProductEntryCollectionModelToDto(final ProductEntryCollection productEntryCollection) {
        return ProductEntryCollectionDto.builder()
                .id(UUID.fromString(productEntryCollection.getId()))
                .type(mapProductEntryCollectionTypeModelToDto(productEntryCollection.getType()))
                .productEntries(productEntryCollection.getProductEntries().stream().map(productEntryMapper::mapProductEntryModelToDto).toList())
                .build();
    }

    private ProductEntryCollectionTypeDto mapProductEntryCollectionTypeModelToDto(final ProductEntryCollectionType type) {
        return switch (type) {
            case SHOPPING_CART -> ProductEntryCollectionTypeDto.SHOPPING_CART;
            case SUPPLIES -> ProductEntryCollectionTypeDto.SUPPLIES;
        };
    }



}
