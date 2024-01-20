package com.financetracker.product.logic.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    private BigDecimal quantity;

    private BigDecimal desiredQuantity;

    @Setter
    private boolean purchased;

}
