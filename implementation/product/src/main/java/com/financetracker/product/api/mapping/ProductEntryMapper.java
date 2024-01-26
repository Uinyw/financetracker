package com.financetracker.product.api.mapping;

import com.financetracker.product.logic.model.Product;
import com.financetracker.product.logic.model.ProductEntry;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.ProductEntryDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductEntryMapper {

    private final ProductMapper productMapper;

    public ProductEntryDto mapProductEntryModelToDto(final ProductEntry productEntry) {
        return ProductEntryDto.builder()
                .id(UUID.fromString(productEntry.getId()))
                .product(productMapper.mapProductModelToDto(productEntry.getProduct()))
                .quantity(productEntry.getQuantity())
                .desiredQuantity(productEntry.getDesiredQuantity())
                .purchased(productEntry.isPurchased())
                .build();
    }

    public ProductEntry mapProductEntryDtoToModel(final ProductEntryDto productEntry) {
        return ProductEntry.with()
                .id(productEntry.getId().toString())
                .product(productMapper.mapProductDtoToModel(productEntry.getProduct()))
                .quantity(productEntry.getQuantity())
                .desiredQuantity(productEntry.getDesiredQuantity())
                .purchased(productEntry.getPurchased())
                .build();
    }
}
