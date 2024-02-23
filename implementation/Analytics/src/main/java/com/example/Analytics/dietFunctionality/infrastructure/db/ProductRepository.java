package com.example.Analytics.dietFunctionality.infrastructure.db;


import com.example.Analytics.dietFunctionality.logic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
