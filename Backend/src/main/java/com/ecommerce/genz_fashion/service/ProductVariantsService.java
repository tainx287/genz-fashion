package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.genz_fashion.entity.ProductVariants;
import com.ecommerce.genz_fashion.repository.ProductVariantsRepository;

@Service
public class ProductVariantsService {
    
    @Autowired
    private ProductVariantsRepository productVariantsRepository;
    
    public List<ProductVariants> getAllProductVariants() {
        return productVariantsRepository.findAll();
    }
    
    public Optional<ProductVariants> getProductVariantById(Long id) {
        return productVariantsRepository.findById(id);
    }
    
    public ProductVariants saveProductVariant(ProductVariants productVariant) {
        if (productVariant.getCreatedAt() == null) {
            productVariant.setCreatedAt(new Date());
        }
        productVariant.setUpdatedAt(new Date());
        return productVariantsRepository.save(productVariant);
    }
    
    public void deleteProductVariant(Long id) {
        productVariantsRepository.deleteById(id);
    }
    
    public List<ProductVariants> getActiveProductVariants() {
        return productVariantsRepository.findAll().stream()
                .filter(variant -> variant.getIsActive() != null && variant.getIsActive())
                .toList();
    }
    
    public List<ProductVariants> getVariantsByProductId(Long productId) {
        return productVariantsRepository.findAll().stream()
                .filter(variant -> variant.getProductId().equals(productId))
                .filter(variant -> variant.getIsActive() != null && variant.getIsActive())
                .toList();
    }
    
    public boolean isVariantInStock(Long variantId) {
        Optional<ProductVariants> variant = getProductVariantById(variantId);
        return variant.isPresent() && 
               variant.get().getQuantityInStock() != null && 
               variant.get().getQuantityInStock() > 0;
    }
    
    public boolean updateStock(Long variantId, Integer newQuantity) {
        Optional<ProductVariants> variant = getProductVariantById(variantId);
        if (variant.isPresent()) {
            variant.get().setQuantityInStock(newQuantity);
            saveProductVariant(variant.get());
            return true;
        }
        return false;
    }
    
    public boolean reduceStock(Long variantId, Integer quantity) {
        Optional<ProductVariants> variant = getProductVariantById(variantId);
        if (variant.isPresent() && variant.get().getQuantityInStock() >= quantity) {
            variant.get().setQuantityInStock(variant.get().getQuantityInStock() - quantity);
            saveProductVariant(variant.get());
            return true;
        }
        return false;
    }
}