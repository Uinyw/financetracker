package com.financetracker.product.logic.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(builderMethodName = "with")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_entry")
@Entity
public class ProductEntry {

    @Id
    private String id;

    @Setter
    @Column(name = "collection_id")
    private String collectionId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    @Setter
    private BigDecimal quantity;

    private BigDecimal desiredQuantity;

    @Setter
    private boolean purchased;

    public boolean shouldBeStockedUp() {
        return quantity.compareTo(desiredQuantity) < 0;
    }

    public ProductEntry entryToStockUp() {
        return ProductEntry.with()
                .id(UUID.randomUUID().toString())
                .collectionId(collectionId)
                .product(product)
                .quantity(desiredQuantity.subtract(quantity))
                .desiredQuantity(desiredQuantity.subtract(quantity))
                .build();
    }

    public boolean equalsProductEntryBasedOnProduct(final ProductEntry productEntry) {
        return product.getId().equals(productEntry.getProduct().getId());
    }

}
