package com.example.Analytics.dietFunctionality.api;

import com.example.Analytics.dietFunctionality.api.mapping.ProductMapper;
import com.example.Analytics.dietFunctionality.api.mapping.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DietResource{
    //todo implements dietApi
    private final ProductService productService;
    private final ProductMapper productMapper;

}
