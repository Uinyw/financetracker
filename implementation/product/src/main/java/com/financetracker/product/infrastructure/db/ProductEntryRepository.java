package com.financetracker.product.infrastructure.db;

import com.financetracker.product.logic.model.ProductEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEntryRepository extends JpaRepository<ProductEntry, String> {

}
