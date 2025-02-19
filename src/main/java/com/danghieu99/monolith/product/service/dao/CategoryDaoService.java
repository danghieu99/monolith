package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.repository.jpa.CategoryRepository;
import com.danghieu99.monolith.product.repository.jpa.join.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryDaoService {

    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    public Category save(Category category) {
        if (category.getId() != null) {
            throw new IllegalArgumentException("New category id must be null");
        }
        return categoryRepository.save(category);
    }

    public Category getById(@NotNull final int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @Transactional
    public Category update(Category category) {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Update category id must not be null");
        }
        return categoryRepository.save(category);
    }

    public Category getByUUID(@NotNull final UUID uuid) {
        return categoryRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Category", "uuid", uuid.toString()));
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> getBySuperCategoryUUID(final UUID superCategoryUUID, final Pageable pageable) {
        return categoryRepository.findBySuperCategoryUUID(superCategoryUUID, pageable);
    }

    public Page<Category> getByNameContaining(@NotNull final String name, final Pageable pageable) {
        return categoryRepository.findByNameContaining(name, pageable);
    }

    public Page<Category> getBySubCategoryUUID(@NotNull final UUID subCategoryUUID, Pageable pageable) {
        return categoryRepository.findBySubCategoryUUID(subCategoryUUID, pageable);
    }

    public Set<Category> getByProductUUID(@NotNull final UUID productUUID) {
        return categoryRepository.findByProductUUID(productUUID);
    }

    @Transactional
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
        productCategoryRepository.deleteByCategoryId(id);
    }
}