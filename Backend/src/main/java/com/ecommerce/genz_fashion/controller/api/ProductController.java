package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.dto.ProductDto;
import com.ecommerce.genz_fashion.entity.Products;
import com.ecommerce.genz_fashion.mapper.ProductMapper;
import com.ecommerce.genz_fashion.service.IProductService;
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
    
    private final IProductService productService;
    private final ProductMapper productMapper; // Inject mapper
    
    @GetMapping
    public ResponseEntity<List<ProductDto.ProductResponse>> getAllProducts() {
        List<Products> products = productService.getActiveProducts();
        List<ProductDto.ProductResponse> productDTOs = productMapper.toProductDTOs(products);
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto.ProductResponse> getProductById(@PathVariable Long id) {
        Products product = productService.getProductById(id);
        ProductDto.ProductResponse productDTO = productMapper.toProductDTO(product);
        return ResponseEntity.ok(productDTO);
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<ProductDto.ProductResponse>> getFeaturedProducts() {
        List<Products> products = productService.getFeaturedProducts();
        return ResponseEntity.ok(productMapper.toProductDTOs(products));
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto.ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Products> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productMapper.toProductDTOs(products));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto.ProductResponse>> searchProducts(@Valid @ModelAttribute ProductDto.ProductSearchRequest request) {
        Page<Products> productPage = productService.searchProducts(request);
        Page<ProductDto.ProductResponse> productDTOPage = productPage.map(productMapper::toProductDTO);
        return ResponseEntity.ok(productDTOPage);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDto.ProductResponse>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Products> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(productMapper.toProductDTOs(products));
    }
    
    @GetMapping("/in-stock")
    public ResponseEntity<List<ProductDto.ProductResponse>> getInStockProducts() {
        List<Products> products = productService.getInStockProducts();
        return ResponseEntity.ok(productMapper.toProductDTOs(products));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ProductDto.ProductResponse> createProduct(@Valid @RequestBody ProductDto.CreateProductRequest request) {
        Products createdProduct = productService.createProduct(request);
        return ResponseEntity.ok(productMapper.toProductDTO(createdProduct));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ProductDto.ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto.UpdateProductRequest request) {
        Products updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(productMapper.toProductDTO(updatedProduct));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}