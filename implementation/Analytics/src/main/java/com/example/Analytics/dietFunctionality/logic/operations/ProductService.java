package com.example.Analytics.dietFunctionality.logic.operations;


import com.example.Analytics.dietFunctionality.api.mapping.ProductMapper;
import com.example.Analytics.dietFunctionality.logic.model.*;
import com.example.Analytics.dietFunctionality.infrastructure.db.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.ProductDto;
import org.openapitools.model.NutritionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public Optional<Product> getProduct(final String productId) {
        return productRepository.findById(UUID.fromString(productId));
    }

    public void createProduct(final Product product) {
        // if product already present, it's just an update -> check product id
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if(optionalProduct.isEmpty() || product.getConsumption().getDate().equals(optionalProduct.get().getConsumption().getDate())){
            productRepository.save(product);
        }else{
            Product prod = optionalProduct.get();
            Consumption cons = prod.getConsumption();
            cons.setAmount(cons.getAmount()+1);
            prod.setConsumption(cons);
            updateProduct(prod);
        }

    }

    public void updateProduct(final Product product) {
        deleteProduct(product.getId().toString());
        productRepository.save(product);
    }

    public void deleteProduct(final String productId) {
        productRepository.deleteById(UUID.fromString(productId));
    }

    public void receiveProduct(ProductDto productDto, double amount){
        Product product = productMapper.productFromDTO(productDto, amount);
        createProduct(product);
    }
}
