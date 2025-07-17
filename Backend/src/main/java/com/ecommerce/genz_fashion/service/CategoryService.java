package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.entity.Categories;
import com.ecommerce.genz_fashion.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public List<Categories> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }
    
    public Optional<Categories> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public List<Categories> getParentCategories() {
        return categoryRepository.findByParentIdIsNull();
    }
    
    public List<Categories> getSubCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }
    
    public Categories createCategory(Categories category) {
        category.setIsActive(true);
        category.setCreatedAt(new Date());
        return categoryRepository.save(category);
    }
    
    public Categories updateCategory(Long id, Categories categoryDetails) {
        Categories category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setParentId(categoryDetails.getParentId());
        
        return categoryRepository.save(category);
    }
    
    public void deleteCategory(Long id) {
        Categories category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        // Check if category has subcategories
        List<Categories> subCategories = categoryRepository.findByParentId(id);
        if (!subCategories.isEmpty()) {
            throw new RuntimeException("Cannot delete category with subcategories");
        }
        
        category.setIsActive(false);
        category.setDeletedAt(new Date());
        categoryRepository.save(category);
    }
    
    public List<Categories> searchCategories(String keyword) {
        return categoryRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}