package com.financetracker.product.logic.operations;

import com.financetracker.product.infrastructure.client.TransactionProvider;
import com.financetracker.product.infrastructure.db.ProductEntryCollectionRepository;
import com.financetracker.product.infrastructure.db.ProductEntryRepository;
import com.financetracker.product.logic.model.MonetaryAmount;
import com.financetracker.product.logic.model.ProductEntry;
import com.financetracker.product.logic.model.ProductEntryCollection;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final TransactionProvider transactionProvider;
    private final ProductEntryRepository productEntryRepository;
    private final ProductEntryCollectionRepository shoppingCartRepository;

    private final ProductEntryCollectionService productEntryCollectionService;

    public ProductEntryCollection getShoppingCart() {
        return productEntryCollectionService.getProductEntryCollection(ProductEntryCollectionType.SHOPPING_CART);
    }

    public void addProductEntryToShoppingCart(final ProductEntry productEntry) {
        productEntryCollectionService.addProductEntryToCollection(ProductEntryCollectionType.SHOPPING_CART, productEntry);
    }

    public void updateProductEntryInShoppingCart(final String productEntryId, final ProductEntry productEntry) {
        productEntryCollectionService.updateProductEntryInCollection(ProductEntryCollectionType.SHOPPING_CART, productEntryId, productEntry);
    }

    public void removeProductEntryFromShoppingCart(final String productEntryId) {
        productEntryCollectionService.removeProductEntryFromCollections(ProductEntryCollectionType.SHOPPING_CART, productEntryId);
    }

    public void markProductEntryAsPurchased(final String productEntryId) {
        final var shoppingCart = getShoppingCart();

        final var productEntry = shoppingCart.getProductEntries().stream().filter(productEntryCollectionService.productEntryWithIdPredicate(productEntryId)).findFirst().orElseThrow(NotFoundException::new);
        productEntry.setPurchased(true);

        productEntryRepository.save(productEntry);
    }

    public void purchaseShoppingCart(final UUID sourceBankAccountId) {
        final var shoppingCart = getShoppingCart();

        final var purchasedProducts = shoppingCart.getProductEntries().stream()
                .filter(ProductEntry::isPurchased)
                .collect(Collectors.toSet());

        final var price = purchasedProducts.stream()
            .mapToDouble(productEntry -> productEntry.getProduct().getPrice().amount().multiply(productEntry.getQuantity()).doubleValue())
            .sum();

        transactionProvider.bookExpense(sourceBankAccountId, new MonetaryAmount(new BigDecimal(price)));

        shoppingCart.getProductEntries().removeAll(purchasedProducts);
        shoppingCartRepository.save(shoppingCart);
    }

}
