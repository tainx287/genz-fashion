package com.ecommerce.genz_fashion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse {
        private Long productId;
        private String name;
        private String description;
        private BigDecimal price;
        private BigDecimal salePrice;
        private Integer stockQuantity;
        private String imageUrl;
        private String size;
        private String color;
        private String brand;
        private String categoryName;
        private Boolean isFeatured;
        private Boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRequest {
        @NotBlank(message = "Product name is required")
        private String name;
        
        private String description;
        
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        private BigDecimal price;
        
        @DecimalMin(value = "0.0", message = "Sale price must be greater than or equal to 0")
        private BigDecimal salePrice;
        
        @NotNull(message = "Stock quantity is required")
        private Integer stockQuantity;
        
        private String imageUrl;
        private String size;
        private String color;
        private String brand;
        
        @NotNull(message = "Category is required")
        private Long categoryId;
        
        private Boolean isFeatured = false;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSummary {
        private Long productId;
        private String name;
        private BigDecimal price;
        private BigDecimal salePrice;
        private String imageUrl;
        private String brand;
        private Boolean isFeatured;
        private Integer stockQuantity;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSearchRequest {
        private String keyword;
        private Long categoryId;
        private String brand;
        private String color;
        private String size;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private Boolean inStock;
        private Boolean featured;
        private String sortBy = "name";
        private String sortDirection = "asc";
        private Integer page = 0;
        private Integer size = 10;
    }
}