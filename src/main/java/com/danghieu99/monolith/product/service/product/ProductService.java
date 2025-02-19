package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.dto.response.ProductDetailsResponse;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.service.dao.CategoryDaoService;
import com.danghieu99.monolith.product.service.dao.ProductDaoService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDaoService productDaoService;
    private final ProductMapper productMapper;
    private final CategoryDaoService categoryDaoService;

    public Page<Product> getAll(@NotNull Pageable pageable) {
        return productDaoService.getAll(pageable);
    }

    public Page<Product> getByCategoriesContains(@NotNull int categoryId, @NotNull Pageable pageable) {
        return productDaoService.getByCategoryId(categoryId, pageable);
    }

    public ProductDetailsResponse getProductDetailsByUUID(@NotBlank String uuid) {
        var product = productDaoService.getByUUID(UUID.fromString(uuid));
        var response = productMapper.toGetProductDetailsResponse(product);
        response.setCategories(categoryDaoService.getByProductUUID(product.getUuid()));
        return response;
    }
}