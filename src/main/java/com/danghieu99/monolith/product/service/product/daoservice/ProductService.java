package com.danghieu99.monolith.product.service.product.daoservice;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.repository.jpa.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product save(@NotNull Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("New product id must be null");
        }
        return productRepository.save(product);
    }

    @Transactional
    public Collection<Product> saveAll(@NotEmpty Collection<Product> products) {
        return productRepository.saveAll(products);
    }

    @Transactional
    public Product update(@NotNull int id, @NotNull Product product) {
        return productRepository.save(product);
    }

    public Product getById(@NotNull Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    public Product getByUUID(@NotNull UUID uuid) {
        return productRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Product", "uuid", uuid));
    }

    public Product getByName(@NotEmpty String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
    }

    public Product getByUuid(@NotEmpty String uuid) {
        return productRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "uuid", uuid));
    }

    public Page<Product> searchByName(@NotNull String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    @Transactional
    public void deleteById(@NotNull Integer id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUUID(@NotNull UUID uuid) {
        productRepository.deleteByUuid(uuid);
    }

    public Page<Product> getByShopId(@NotNull Integer shopId, Pageable pageable) {
        return productRepository.findByShopId(shopId, pageable);
    }

    public Page<Product> getByShopUUID(@NotNull UUID shopId, Pageable pageable) {
        return productRepository.findByShopUUID(shopId, pageable);
    }

    public Page<Product> searchByParams(String name, Set<String> categories, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByNameContainingAndCategoryNameContainingAndPriceRange(name, categories, minPrice, maxPrice, pageable);
    }
}