package com.ecommerce.genzfashion.service;

import com.ecommerce.genzfashion.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    long countAllProducts();
    Page<Product> getProductsWithPagination(Pageable pageable);
}