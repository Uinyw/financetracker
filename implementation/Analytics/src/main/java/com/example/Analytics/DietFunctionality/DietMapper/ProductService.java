package com.example.Analytics.DietFunctionality.DietMapper;

import com.example.Analytics.BudgetFunctionality.VariableMonthlyTransactions;
import com.example.Analytics.DietFunctionality.Product;
import com.example.Analytics.DietFunctionality.Repo.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductService {
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public Optional<Product> getProduct(final String productId) {
        return productRepository.findById(productId);
    }

    public void createProduct(final Product product) {
        productRepository.save(product);
    }

    public void updateProduct(final Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(final String productId) {
        productRepository.deleteById(productId);
    }

}
