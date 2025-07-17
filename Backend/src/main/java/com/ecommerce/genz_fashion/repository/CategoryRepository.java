package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    
    List<Categories> findByIsActiveTrue();
    
    List<Categories> findByParentIdIsNull();
    
    List<Categories> findByParentId(Long parentId);
    
    List<Categories> findByNameContainingIgnoreCase(String name);
    
    boolean existsByName(String name);
}