package com.financetracker.product.api;

import com.financetracker.product.api.mapping.ProductEntryCollectionMapper;
import com.financetracker.product.logic.operations.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ShoppingCartApi;
import org.openapitools.model.ProductEntryCollectionDto;
import org.openapitools.model.ProductEntryDto;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ShoppingCartResource implements ShoppingCartApi {

    private final ShoppingCartService shoppingCartService;
    private final ProductEntryCollectionMapper shoppingCartMapper;

    @Override
    public void shoppingCartEntriesIdCheckPost(String id) {

    }

    @Override
    public void shoppingCartEntriesIdDelete(String id) {

    }

    @Override
    public void shoppingCartEntriesIdPatch(String id, ProductEntryDto productEntryDto) {

    }

    @Override
    public void shoppingCartEntriesPost(ProductEntryDto productEntryDto) {

    }

    @Override
    public ProductEntryCollectionDto shoppingCartGet() {
        var x = 0;
        var y = 0;
        return shoppingCartMapper.mapProductEntryCollectionModelToDto(shoppingCartService.getShoppingCart());
    }

    @Override
    public void shoppingCartPurchasePost() {

    }
}
