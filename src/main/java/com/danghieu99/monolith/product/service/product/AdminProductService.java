package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductCrudService productCrudService;

    public List<Product> findAll() {
        return productCrudService.getAll();
    }

    public Product findById(int id) {
        return productCrudService.getById(id);
    }

    public Product save(Product product) {
        return productCrudService.create(product);
    }

    public Product update(Product product) {
        return productCrudService.update(product);
    }

    public void delete(int id) {
        productCrudService.deleteById(id);
    }
}
