package com.financetracker.product.api;

import com.financetracker.product.api.mapping.ProductEntryCollectionMapper;
import com.financetracker.product.api.mapping.ProductEntryMapper;
import com.financetracker.product.logic.operations.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ShoppingCartApi;
import org.openapitools.model.ProductEntryCollectionDto;
import org.openapitools.model.ProductEntryDto;
import org.openapitools.model.PurchaseDto;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ShoppingCartResource implements ShoppingCartApi {

    private final ShoppingCartService shoppingCartService;
    private final ProductEntryMapper productEntryMapper;
    private final ProductEntryCollectionMapper shoppingCartMapper;

    @Override
    public void shoppingCartEntriesIdCheckPost(String id) {
        shoppingCartService.markProductEntryAsPurchased(id);
    }

    @Override
    public void shoppingCartEntriesIdDelete(String id) {
        shoppingCartService.removeProductEntryFromShoppingCart(id);
    }

    @Override
    public void shoppingCartEntriesIdPatch(String id, ProductEntryDto productEntryDto) {
        shoppingCartService.updateProductEntryInShoppingCart(id, productEntryMapper.mapProductEntryDtoToModel(productEntryDto));
    }

    @Override
    public void shoppingCartEntriesPost(ProductEntryDto productEntryDto) {
        shoppingCartService.addProductEntryToShoppingCart(productEntryMapper.mapProductEntryDtoToModel(productEntryDto));
    }

    @Override
    public ProductEntryCollectionDto shoppingCartGet() {
        return shoppingCartMapper.mapProductEntryCollectionModelToDto(shoppingCartService.getShoppingCart());
    }

    @Override
    public void shoppingCartPurchasePost(PurchaseDto purchaseDto) {
        shoppingCartService.purchaseShoppingCart(purchaseDto.getSourceBankAccountId());
    }
}
