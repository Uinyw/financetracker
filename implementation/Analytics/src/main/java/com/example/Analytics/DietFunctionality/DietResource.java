package com.example.Analytics.DietFunctionality;

import com.example.Analytics.DietFunctionality.DietMapper.ProductMapper;
import com.example.Analytics.DietFunctionality.DietMapper.ProductService;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.api.ProductApi;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DietResource{
    //todo implements dietApi
    private final ProductService productService;
    private final ProductMapper productMapper;

}
