package com.financetracker.product.logic.operations;

import com.financetracker.product.infrastructure.db.ProductEntryCollectionRepository;
import com.financetracker.product.logic.model.ProductEntryCollection;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final ProductEntryCollectionRepository shoppingCartRepository;

    public ProductEntryCollection getShoppingCart() {
        if (shoppingCartRepository.findAll().isEmpty()) {
            final var shoppingCart = createShoppingCart();
            shoppingCartRepository.save(shoppingCart);
        }

        return shoppingCartRepository.findAll().get(0);
    }

    private ProductEntryCollection createShoppingCart() {
        return ProductEntryCollection.with()
                .id(UUID.randomUUID().toString())
                .type(ProductEntryCollectionType.SHOPPING_CART)
                .build();

    }
}
