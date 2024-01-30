package com.financetracker.product.api;

import com.financetracker.product.api.mapping.ProductMapper;
import com.financetracker.product.logic.operations.ProductService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ProductsApi;
import org.openapitools.model.ProductDto;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductResource implements ProductsApi {


    private final ProductMapper productMapper;
    private final ProductService productService;

    @Override
    public List<ProductDto> productsGet() {
        return productService.getProducts().stream()
                .map(productMapper::mapProductModelToDto)
                .toList();
    }

    @Override
    public void productsIdDelete(String id) {
        productService.deleteProductById(id);
    }

    @Override
    public ProductDto productsIdGet(String id) {
        final var product = productService.getProductById(id).orElseThrow(NotFoundException::new);
        return productMapper.mapProductModelToDto(product);
    }

    @Override
    public void productsIdPatch(String id, ProductDto productDto) {
        productService.updateProduct(id, productMapper.mapProductDtoToModel(productDto));
    }

    @Override
    public void productsPost(ProductDto productDto) {
        productService.createProduct(productMapper.mapProductDtoToModel(productDto));
    }
}
