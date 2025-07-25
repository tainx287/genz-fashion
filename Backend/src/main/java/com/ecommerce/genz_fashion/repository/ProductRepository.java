package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    
    List<Products> findByIsActiveTrue();
    
    List<Products> findByIsFeaturedTrue();
    
    List<Products> findByCategoryCategoryId(Long categoryId);
    
    List<Products> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT p FROM Products p WHERE p.basePrice BETWEEN :minPrice AND :maxPrice AND p.isActive = true")
    List<Products> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT p FROM Products p WHERE p.isActive = true")
    List<Products> findInStockProducts();
    
    Page<Products> findByIsActiveTrue(Pageable pageable);
    
    @Query("SELECT p FROM Products p WHERE " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))) AND " +
           "p.isActive = true")
    Page<Products> searchProducts(@Param("keyword") String keyword, Pageable pageable);
}