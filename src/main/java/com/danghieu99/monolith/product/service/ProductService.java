package com.danghieu99.monolith.product.service;

import com.danghieu99.monolith.auth.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.repository.jpa.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("New product id must be null");
        }
        return productRepository.save(product);
    }

    public Product update(Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Update product id must not be null");
        }
        return productRepository.save(product);
    }

    public Product getById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    public Product getByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
    }

    public Product getByUuid(String uuid) {
        return productRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "uuid", uuid));
    }
}
