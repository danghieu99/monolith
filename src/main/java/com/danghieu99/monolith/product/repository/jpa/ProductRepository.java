package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByUuid(UUID uuid);

    Optional<Product> findByName(String name);

    @Query("select p from Product p where p.name like %:name%")
    List<Product> searchByName(String name);

    @Query("select p from Product p where p.name like %:name%")
    Page<Product> searchByName(String name, Pageable pageable);

    @Query("select p from Product p join p.categories c where c = :category")
    List<Product> findByCategoriesContains(Category category);

    @Query("select p from Product p join p.categories c where c = :category")
    Page<Product> findByCategoriesContains(Category category, Pageable pageable);

    @Query("select p from Product p join p.categories c where c in :categories")
    List<Product> findByCategoriesContainsAny(Collection<Category> categories);

    @Query("select p from Product p join p.categories c where c in :categories")
    Page<Product> findByCategoriesContainsAny(Collection<Category> categories, Pageable pageable);
}


