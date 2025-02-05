package com.danghieu99.monolith.product.service.category;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.repository.jpa.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryCrudService {

    private final CategoryRepository repository;

    public Category create(Category category) {
        if (category.getId() != null) {
            throw new IllegalArgumentException("New category id must be null");
        }
        return repository.save(category);
    }

    public Category update(Category category) {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Update category id must not be null");
        }
        return repository.save(category);
    }

    public void delete(Category category) {
        repository.delete(category);
    }

    public Category getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
