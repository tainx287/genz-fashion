package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.entity.Products;
import com.ecommerce.genz_fashion.entity.Categories;
import com.ecommerce.genz_fashion.entity.Brands;
import com.ecommerce.genz_fashion.repository.ProductRepository;
import com.ecommerce.genz_fashion.repository.CategoryRepository;
import com.ecommerce.genz_fashion.repository.BrandsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandsRepository brandsRepository;
    
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
    
    public List<Products> getActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }
    
    public Optional<Products> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Products> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue();
    }
    
    public List<Products> getProductsByCategory(Long categoryId) {
        Categories category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return productRepository.findByCategory(category);
    }
    
    public List<Products> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    public Page<Products> searchProductsWithPagination(String keyword, Pageable pageable) {
        return productRepository.searchProducts(keyword, pageable);
    }
    
    public List<Products> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public List<Products> getInStockProducts() {
        return productRepository.findInStockProducts();
    }
    
    public Products createProduct(Products product) {
        return productRepository.save(product);
    }
    
    public Products updateProduct(Long id, Products productDetails) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setBasePrice(productDetails.getBasePrice());
        
        // Sử dụng JPA relationships
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }
        if (productDetails.getBrand() != null) {
            product.setBrand(productDetails.getBrand());
        }
        
        product.setSku(productDetails.getSku());
        product.setBarcode(productDetails.getBarcode());
        
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setIsActive(false);
        productRepository.save(product);
    }
}