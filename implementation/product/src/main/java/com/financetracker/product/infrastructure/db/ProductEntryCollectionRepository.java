package com.financetracker.product.infrastructure.db;

import com.financetracker.product.logic.model.ProductEntryCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEntryCollectionRepository extends JpaRepository<ProductEntryCollection, String> {

}
