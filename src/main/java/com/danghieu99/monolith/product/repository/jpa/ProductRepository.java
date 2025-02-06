package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Shop;
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
    List<Product> findByNameContaining(String name);

    @Query("select p from Product p where p.name like %:name%")
    Page<Product> findByNameContaining(String name, Pageable pageable);

    List<Product> findByCategories(Set<Category> categories);

    Page<Product> findByCategories(Set<Category> categories, Pageable pageable);

    @Query("select p from Category c join c.products p where p = :product")
    List<Product> findBySameCategories(Product product);

    @Query("select p from Product p join p.categories c where c = :category")
    List<Product> findByCategoriesContaining(Category category);

    @Query("select p from Product p join p.categories c where c = :category")
    Page<Product> findByCategoriesContaining(Category category, Pageable pageable);

    @Query("select p from Product p join p.categories c where c in :categories")
    List<Product> findByCategoriesContainingAny(Collection<Category> categories);

    @Query("select p from Product p join p.categories c where c in :categories")
    Page<Product> findByCategoriesContainingAny(Collection<Category> categories, Pageable pageable);

    @Query("select p from Product p join p.categories c where p.name like concat('%' , :keyword, '%') or c.name like concat('%' , :keyword, '%')")
    List<Product> findByNameOrCategoryContaining(String keyword);

    @Query("select p from Product p join p.shop s where s = :shop")
    List<Product> findByShop(Shop shop);

    @Query("select p from Product p join p.shop s where s = :shop")
    Page<Product> findByShop(Shop shop, Pageable pageable);

    @Query("select p from Product p join p.shop s where s.id = :id")
    List<Product> findByShopId(int id);

    @Query("select p from Product p join p.shop s where s.id = :id")
    Page<Product> findByShopId(int id, Pageable pageable);

    @Query("select p from Product p join p.shop s where s.uuid = :uuid")
    List<Product> findByShopUuid(UUID uuid);

    @Query("select p from Product p join p.shop s where s.uuid = :uuid")
    Page<Product> findByShopUUID(UUID uuid, Pageable pageable);

    void setStatusById(int id, EProductStatus status);

    void setStatusByUuid(UUID uuid, EProductStatus status);
}