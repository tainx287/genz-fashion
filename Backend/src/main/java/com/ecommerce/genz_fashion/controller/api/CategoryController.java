package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.entity.Categories;
import com.ecommerce.genz_fashion.service.CategoryService;
import com.ecommerce.genz_fashion.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
    
    @GetMapping("/parent")
    public ResponseEntity<List<Categories>> getParentCategories() {
        List<Categories> categories = categoryService.getParentCategories();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<List<Categories>> getSubCategories(@PathVariable Long parentId) {
        List<Categories> categories = categoryService.getSubCategories(parentId);
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Categories>> searchCategories(@RequestParam String keyword) {
        List<Categories> categories = categoryService.searchCategories(keyword);
        return ResponseEntity.ok(categories);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Categories> createCategory(@Valid @RequestBody Categories category) {
        Categories createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Categories> updateCategory(@PathVariable Long id, @Valid @RequestBody Categories categoryDetails) {
        // Service của bạn nên throw ResourceNotFoundException nếu không tìm thấy category
        Categories updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        // Service của bạn nên throw exception nếu có lỗi
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content là lựa chọn tốt hơn cho việc xóa thành công
    }
}