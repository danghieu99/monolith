package com.danghieu99.monolith.product.service.product.daoservice;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.repository.jpa.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional
    public Category create(Category category) {
        if (category.getId() != null) {
            throw new IllegalArgumentException("New category id must be null");
        }
        return repository.save(category);
    }

    public Category getById(@NotNull final int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @Transactional
    public Category update(Category category) {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Update category id must not be null");
        }
        return repository.save(category);
    }

    public Category getByUUID(@NotNull final UUID uuid) {
        return repository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Category", "uuid", uuid.toString()));
    }

    public Category getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Page<Category> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Category> getBySuperCategoryUUID(final UUID superCategoryUUID, final Pageable pageable) {
        return repository.findBySuperCategoryUUID(superCategoryUUID, pageable);
    }

    public Page<Category> getByNameContaining(@NotNull final String name, final Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    public Page<Category> getBySubCategoryUUID(@NotNull final UUID subCategoryUUID, Pageable pageable) {
        return repository.findBySubCategoryUUID(subCategoryUUID, pageable);
    }

    public List<Category> getByProductUUID(@NotNull final UUID productUUID) {
        return repository.findByProductUUID(productUUID);
    }

    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}