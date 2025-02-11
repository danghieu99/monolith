package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByUuid(UUID uuid);

    Optional<Product> findByName(String name);

    @Query("select p from Product p where p.name like %:name%")
    List<Product> findByNameContaining(String name);

    @Query("select p from Product p join ProductCategory pc on p.id = pc.productId where pc.productId = :productId")
    List<Product> findBySameCategoryProductId(int productId);

    @Query("select p from Product p join ProductCategory pc on p.id = pc.productId where pc.categoryId = :categoryId")
    List<Product> findByCategoryId(int categoryId);

    @Query("select p from Product p join ProductCategory pc on p.id = pc.productId where pc.categoryId = :categoryId")
    Page<Product> findByCategoryId(Category category, Pageable pageable);

    @Query("select p from Product p join ProductCategory pc on p.id = pc.productId where pc.categoryId in :categoryIds")
    List<Product> findByCategoryAnyOf(Collection<Integer> categoryIds);

    @Query("select p from Product p join ProductCategory pc on p.id = pc.productId where pc.categoryId in :categoryIds")
    Page<Product> findByCategoryAnyOf(Collection<Integer> categoryIds, Pageable pageable);

    @Query("select p from Product p join Shop s on s.id = p.shopId where s.id = :shopId")
    List<Product> findByShopId(int id);

    @Query("select p from Product p join Shop s on s.id = p.shopId where s.id = :shopId")
    Page<Product> findByShopId(int shopId, Pageable pageable);

    @Query("select p from Product p join Shop s on s.id = p.shopId where s.uuid = :uuid")
    List<Product> findByShopUuid(UUID uuid);

    @Query("select p from Product p join Shop s on s.id = p.shopId where s.uuid = :uuid")
    Page<Product> findByShopUUID(UUID uuid, Pageable pageable);

    @Query("select p from Product p where p.name like %:name%")
    Page<Product> findByNameContaining(String name, Pageable pageable);

    @Query("select p from Product p " +
            "join ProductCategory pc on p.id = pc.productId " +
            "join Category c on c.id = pc.categoryId " +
            "where (:name is null or p.name like concat('%', :name, '%'))" +
            "and (:categories is null or c.name like concat('%', :categories, '%'))" +
            "and (:minPrice is null or p.basePrice >= :minPrice)" +
            "and (:maxPrice is null or p.basePrice <= :maxPrice)")
    Page<Product> findByNameContainingAndCategoryNameContainingAndPriceRange(String name, Set<String> categories, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}