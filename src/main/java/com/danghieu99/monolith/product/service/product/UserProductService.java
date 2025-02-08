package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.dto.request.SearchByParamsRequest;
import com.danghieu99.monolith.product.dto.response.GetProductDetailsResponse;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private final ProductCrudService productCrudService;
    private final ProductMapper productMapper;

    public List<Product> getAll() {
        return productCrudService.getAll();
    }

    public Page<Product> searchByParams(SearchByParamsRequest request, Pageable pageable) {
        return productCrudService.searchByParams(request.getName(), request.getCategories(), request.getMinPrice(), request.getMaxPrice(), pageable);
    }

    public GetProductDetailsResponse getProductDetailsByUUID(String uuid) {
        return productMapper.toGetProductDetailsResponse(productCrudService.getByUUID(UUID.fromString(uuid)));
    }
}
