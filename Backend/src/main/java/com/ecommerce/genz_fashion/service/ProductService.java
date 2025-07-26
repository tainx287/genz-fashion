package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.dto.ProductDto;
import com.ecommerce.genz_fashion.entity.Categories;
import com.ecommerce.genz_fashion.entity.Products;
import com.ecommerce.genz_fashion.exception.DuplicateResourceException;
import com.ecommerce.genz_fashion.exception.ResourceNotFoundException;
import com.ecommerce.genz_fashion.repository.ProductRepository;
import com.ecommerce.genz_fashion.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository; // Thêm dependency này

    // Các phương thức GET không thay đổi logic cốt lõi
    public List<Products> getActiveProducts() {
        return productRepository.findByIsActiveTrue(); // Giả sử có phương thức này
    }

    public List<Products> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrueAndIsActiveTrue(); // Giả sử
    }

    public List<Products> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategories_IdAndIsActiveTrue(categoryId); // Giả sử
    }

    @Override
    public Page<Products> searchProducts(ProductDto.ProductSearchRequest request) {
        Sort sort = request.getSortDirection().equalsIgnoreCase(Sort.Direction.DESC.name()) ?
                Sort.by(request.getSortBy()).descending() : Sort.by(request.getSortBy()).ascending();
        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize(), sort);

        // TODO: Implement dynamic query using JPA Specifications for other criteria
        // in ProductSearchRequest (categoryId, brand, price range, etc.).
        // For now, we only search by keyword.
        return productRepository.findByNameContainingAndIsActiveTrue(request.getKeyword(), pageable);
    }

    public List<Products> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetweenAndIsActiveTrue(minPrice, maxPrice); // Giả sử
    }

    public List<Products> getInStockProducts() {
        return productRepository.findByStockGreaterThanAndIsActiveTrue(0); // Giả sử
    }

    // --- PHẦN TÁI CẤU TRÚC QUAN TRỌNG ---

    public Products getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Products createProduct(ProductDto.CreateProductRequest request) {
        // Kiểm tra xem sản phẩm với tên này đã tồn tại chưa
        if (productRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Product with name '" + request.getName() + "' already exists.");
        }

        // Tìm category, nếu không thấy sẽ báo lỗi
        Categories category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Products newProduct = new Products();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setPrice(request.getPrice());
        newProduct.setStock(request.getStock());
        newProduct.setCategories(category);
        newProduct.setFeatured(request.isFeatured());
        newProduct.setActive(true); // Mặc định là active khi tạo mới

        return productRepository.save(newProduct);
    }

    public Products updateProduct(Long id, ProductDto.UpdateProductRequest request) {
        // Tái sử dụng phương thức getProductById để kiểm tra sự tồn tại
        Products existingProduct = getProductById(id);

        // Cập nhật các trường nếu chúng được cung cấp trong request (không phải null)
        if (request.getName() != null) existingProduct.setName(request.getName());
        if (request.getDescription() != null) existingProduct.setDescription(request.getDescription());
        if (request.getPrice() != null) existingProduct.setPrice(request.getPrice());
        if (request.getStock() != null) existingProduct.setStock(request.getStock());
        if (request.getIsFeatured() != null) existingProduct.setFeatured(request.getIsFeatured());
        if (request.getIsActive() != null) existingProduct.setActive(request.getIsActive());

        // Cập nhật category nếu categoryId được cung cấp
        if (request.getCategoryId() != null) {
            Categories category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            existingProduct.setCategories(category);
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}