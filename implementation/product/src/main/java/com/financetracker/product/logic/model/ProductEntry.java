package com.financetracker.product.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_entry")
@Entity
public class ProductEntry {

    @Id
    private String id;

    @Column(name = "collection_id")
    private String collectionId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    private BigDecimal quantity;

    private BigDecimal desiredQuantity;

    private boolean purchased;

}
