package com.danghieu99.monolith.product.service.product.user;

import com.danghieu99.monolith.product.dto.request.SearchProductRequest;
import com.danghieu99.monolith.product.dto.response.ProductDetailsResponse;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.service.product.daoservice.CategoryService;
import com.danghieu99.monolith.product.service.product.daoservice.ProductService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public Page<Product> getAll(@NotNull Pageable pageable) {
        return productService.getAll(pageable);
    }

    public Page<Product> searchByParams(SearchProductRequest request, Pageable pageable) {
        return productService.searchByParams(request.getName(), request.getCategories(), request.getMinPrice(), request.getMaxPrice(), pageable);
    }

    public ProductDetailsResponse getProductDetailsByUUID(String uuid) {
        var product = productService.getByUUID(UUID.fromString(uuid));
        var response = productMapper.toGetProductDetailsResponse(product);
        response.setCategories(categoryService.getByProductUUID(product.getUuid()));
        return response;
    }
}