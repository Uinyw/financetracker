package com.example.Analytics.dietFunctionality.infrastructure.db;


import com.example.Analytics.dietFunctionality.logic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
