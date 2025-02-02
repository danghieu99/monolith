package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private final ProductCrudService productCrudService;

    public List<Product> getAll() {
        return productCrudService.getAll();
    }



}
