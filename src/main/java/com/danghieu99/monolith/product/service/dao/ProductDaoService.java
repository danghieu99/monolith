package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.repository.jpa.ProductRepository;
import com.danghieu99.monolith.product.repository.jpa.join.ProductCategoryRepository;
import com.danghieu99.monolith.product.repository.jpa.join.ProductShopRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDaoService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductShopRepository productShopRepository;
    private final VariantDaoService variantDaoService;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> getAll(@NotNull Pageable pageable) {
        return productRepository.findAll(pageable);
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

    public Page<Product> getByCategoryId(@NotNull int id, Pageable pageable) {
        return productRepository.findByCategoryId(id, pageable);
    }


    public Page<Product> getByShopId(@NotNull Integer shopId, Pageable pageable) {
        return productRepository.findByShopId(shopId, pageable);
    }

    public Page<Product> getByShopUUID(@NotNull UUID shopId, Pageable pageable) {
        return productRepository.findByShopUUID(shopId, pageable);
    }

    @Transactional
    public Product update(@NotNull int id, @NotNull Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteByIdCascading(@NotNull Integer id) {
        productRepository.deleteById(id);
        productCategoryRepository.deleteByProductId(id);
        productShopRepository.deleteByProductId(id);
        variantDaoService.deleteByProductIdCascading(id);
    }

    @Transactional
    public void deleteByUUIDCascading(@NotNull UUID uuid) {
        productRepository.deleteByUuid(uuid);
        productCategoryRepository.deleteByProductUUID(uuid);
        productShopRepository.deleteByProductUUID(uuid);
        variantDaoService.deleteByProductUUIDCascading(uuid);
    }

    @Transactional
    public void deleteByShopId(@NotNull Integer shopId) {
        productRepository.deleteByShopId(shopId);
    }

    @Transactional
    public void deleteByShopUUID(@NotNull UUID shopUUID) {
        productRepository.deleteByShopUUID(shopUUID);
    }
}