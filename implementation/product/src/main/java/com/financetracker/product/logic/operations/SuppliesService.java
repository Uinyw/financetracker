package com.financetracker.product.logic.operations;

import com.financetracker.product.logic.model.ProductEntry;
import com.financetracker.product.logic.model.ProductEntryCollection;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuppliesService {

    private final ProductEntryCollectionService productEntryCollectionService;
    private final ShoppingCartService shoppingCartService;

    public ProductEntryCollection getSupplies() {
        return productEntryCollectionService.getProductEntryCollection(ProductEntryCollectionType.SUPPLIES);
    }

    public void addProductEntryToSupplies(final ProductEntry productEntry) {
        productEntryCollectionService.addProductEntryToCollection(ProductEntryCollectionType.SUPPLIES, productEntry);
    }

    public void updateProductEntryInSupplies(final String productEntryId, final ProductEntry productEntry) {
        productEntryCollectionService.updateProductEntryInCollection(ProductEntryCollectionType.SUPPLIES, productEntryId, productEntry);
    }

    public void removeProductEntryFromShoppingCart(final String productEntryId) {
        productEntryCollectionService.removeProductEntryFromCollections(ProductEntryCollectionType.SUPPLIES, productEntryId);
    }

    public void addProductsToBuyToShoppingCart() {
        final var supplies = getSupplies();

        supplies.getProductEntries().stream()
                .filter(ProductEntry::shouldBeStockedUp)
                .map(ProductEntry::entryToStockUp)
                .forEach(shoppingCartService::addProductEntryToShoppingCart);
    }
}
