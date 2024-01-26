package com.financetracker.product.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder(builderMethodName = "with")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "product_entry_collection")
@Entity
public class ProductEntryCollection {

    @Id
    protected String id;

    protected ProductEntryCollectionType type;

    @JoinColumn(name = "collection_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Set<ProductEntry> productEntries = new HashSet<>();
}
