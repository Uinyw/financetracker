package com.financetracker.product.api;

import com.financetracker.product.api.mapping.ProductEntryCollectionMapper;
import com.financetracker.product.api.mapping.ProductEntryMapper;
import com.financetracker.product.logic.operations.SuppliesService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.SuppliesApi;
import org.openapitools.model.ProductEntryCollectionDto;
import org.openapitools.model.ProductEntryDto;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SuppliesResource implements SuppliesApi {

    private final SuppliesService suppliesService;
    private final ProductEntryMapper productEntryMapper;
    private final ProductEntryCollectionMapper suppliesMapper;

    @Override
    public void suppliesEntriesIdDelete(String id) {
        suppliesService.removeProductEntryFromShoppingCart(id);
    }

    @Override
    public void suppliesEntriesIdPatch(String id, ProductEntryDto productEntryDto) {
        suppliesService.updateProductEntryInSupplies(id, productEntryMapper.mapProductEntryDtoToModel(productEntryDto));
    }

    @Override
    public void suppliesEntriesPost(ProductEntryDto productEntryDto) {
        suppliesService.addProductEntryToSupplies(productEntryMapper.mapProductEntryDtoToModel(productEntryDto));
    }

    @Override
    public ProductEntryCollectionDto suppliesGet() {
        return suppliesMapper.mapProductEntryCollectionModelToDto(suppliesService.getSupplies());
    }

    @Override
    public void suppliesShopPost() {
        suppliesService.addProductsToBuyToShoppingCart();
    }
}
