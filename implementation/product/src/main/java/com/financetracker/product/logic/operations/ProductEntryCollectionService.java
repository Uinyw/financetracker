package com.financetracker.product.logic.operations;

import com.financetracker.product.infrastructure.db.ProductEntryCollectionRepository;
import com.financetracker.product.infrastructure.db.ProductEntryRepository;
import com.financetracker.product.infrastructure.kafka.MessagePublisher;
import com.financetracker.product.logic.model.ProductEntry;
import com.financetracker.product.logic.model.ProductEntryCollection;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class ProductEntryCollectionService {

    private final ProductEntryRepository productEntryRepository;
    private final ProductEntryCollectionRepository productEntryCollectionRepository;
    private final MessagePublisher messagePublisher;

    public ProductEntryCollection getProductEntryCollection(final ProductEntryCollectionType type) {
        return productEntryCollectionRepository.findTopByType(type).orElseThrow(NotFoundException::new);
    }

    public void addProductEntryToCollection(final ProductEntryCollectionType type, final ProductEntry productEntry) {
        final var collection = getProductEntryCollection(type);
        productEntry.setCollectionId(collection.getId());

       collection.getProductEntries().stream()
                .filter(productEntry::equalsProductEntryBasedOnProduct)
                .findFirst()
                .ifPresentOrElse(
                        p -> {
                            p.setQuantity(productEntry.getQuantity());
                            p.setDesiredQuantity(productEntry.getDesiredQuantity());
                        },
                        () -> collection.getProductEntries().add(productEntry)
                );

        productEntryCollectionRepository.save(collection);
    }

    public void updateProductEntryInCollection(final ProductEntryCollectionType type, final String productEntryId, final ProductEntry productEntry) {
        final var collection = getProductEntryCollection(type);

        final var originalProductEntry = collection.getProductEntries().stream().filter(productEntryWithIdPredicate(productEntryId)).findFirst().orElseThrow(NotFoundException::new);

        if (type.equals(ProductEntryCollectionType.SUPPLIES)) {
            final var amount = originalProductEntry.getQuantity().subtract(productEntry.getQuantity());

            if (amount.doubleValue() > 0.0) {
                messagePublisher.publishMessageProductUpdate(productEntry.getProduct(), amount);
            }
        }

        productEntry.setCollectionId(collection.getId());
        productEntryRepository.save(productEntry);
    }

    public void removeProductEntryFromCollections(final ProductEntryCollectionType type, final String productEntryId) {
        final var collection = getProductEntryCollection(type);

        final var productEntryRemoved = collection.getProductEntries().removeIf(productEntryWithIdPredicate(productEntryId));
        if (!productEntryRemoved) {
            throw new NotFoundException();
        }

        productEntryCollectionRepository.save(collection);
    }

    public Predicate<ProductEntry> productEntryWithIdPredicate(final String productEntryId) {
        return productEntry -> productEntry.getId().equals(productEntryId);
    }
}
