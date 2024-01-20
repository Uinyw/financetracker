package com.financetracker.product.infrastructure.db;

import com.financetracker.product.logic.model.ProductEntryCollection;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductEntryCollectionRepository extends JpaRepository<ProductEntryCollection, String> {

    Optional<ProductEntryCollection> findTopByType(ProductEntryCollectionType type);

}
