package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.entity.Products;
import com.ecommerce.genz_fashion.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<Products>> getFeaturedProducts() {
        List<Products> products = productService.getFeaturedProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Products>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Products> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Products>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Products> products = productService.searchProductsWithPagination(keyword, pageable);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<Products>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Products> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/in-stock")
    public ResponseEntity<List<Products>> getInStockProducts() {
        List<Products> products = productService.getInStockProducts();
        return ResponseEntity.ok(products);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Products> createProduct(@Valid @RequestBody Products product) {
        try {
            Products createdProduct = productService.createProduct(product);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @Valid @RequestBody Products productDetails) {
        try {
            Products updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/stock")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            productService.updateStock(id, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}