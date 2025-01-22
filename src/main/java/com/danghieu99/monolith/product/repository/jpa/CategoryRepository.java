package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    @Query("select c from Category c where c.name like concat('%', :name, '%')")
    List<Category> findByNameContaining(String name);

    @Query("select c from Category c where c.name like concat('%', :name, '%')")
    Page<Category> findByNameContaining(String name, Pageable pageable);



}
