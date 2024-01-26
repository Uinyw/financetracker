package com.financetracker.product.logic.operations;

import com.financetracker.product.infrastructure.db.ProductRepository;
import com.financetracker.product.infrastructure.nutrition.NutritionProvider;
import com.financetracker.product.logic.model.Product;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final NutritionProvider nutritionProvider;
    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(final String id) {
        return productRepository.findById(id);
    }

    public void deleteProductById(final String id) {
        productRepository.deleteById(id);
    }

    public void createProduct(final Product product) {
        if (product.nutritionRequested()) {
            setProductNutrition(product);
        }

        productRepository.save(product);
    }

    public void updateProduct(final String productId, final Product product) {
        if (getProductById(productId).isEmpty()) {
            throw new NotFoundException();
        }

        if (product.nutritionRequested()) {
            setProductNutrition(product);
        }

        productRepository.save(product);
    }

    private void setProductNutrition(Product product) {
        final var nutrition = nutritionProvider.getNutritionForProduct(product.getName());
        product.setNutrition(nutrition);
    }
}
