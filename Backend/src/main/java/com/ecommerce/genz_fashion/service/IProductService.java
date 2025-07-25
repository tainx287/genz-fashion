package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.dto.ProductDto;
import com.ecommerce.genz_fashion.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    List<Products> getActiveProducts();
    List<Products> getFeaturedProducts();
    List<Products> getProductsByCategory(Long categoryId);
    Page<Products> searchProductsWithPagination(String keyword, Pageable pageable);
    List<Products> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Products> getInStockProducts();
    Products getProductById(Long id);
    Products createProduct(ProductDto.CreateProductRequest request);
    Products updateProduct(Long id, ProductDto.UpdateProductRequest request);
    void deleteProduct(Long id);
}