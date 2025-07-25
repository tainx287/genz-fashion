package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    
    List<Categories> findByIsActiveTrue();
    
    List<Categories> findByParentIsNull();
    
    List<Categories> findByParent(Categories parent);
    
    List<Categories> findByNameContainingIgnoreCase(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT c FROM Categories c WHERE c.parent IS NULL AND c.isActive = true")
    List<Categories> findActiveParentCategories();
    
    @Query("SELECT c FROM Categories c WHERE c.parent = :parent AND c.isActive = true")
    List<Categories> findActiveSubCategories(@Param("parent") Categories parent);
    
    @Query("SELECT c FROM Categories c WHERE c.isActive = true AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Categories> searchActiveCategories(@Param("keyword") String keyword);
    
    long countByParent(Categories parent);
    
    long countByIsActiveTrue();
}