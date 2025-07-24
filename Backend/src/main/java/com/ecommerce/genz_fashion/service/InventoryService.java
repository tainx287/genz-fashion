package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.genz_fashion.entity.InventoryLogs;
import com.ecommerce.genz_fashion.entity.ProductVariants;
import com.ecommerce.genz_fashion.repository.InventoryLogsRepository;
import com.ecommerce.genz_fashion.repository.ProductVariantsRepository;

@Service
@Transactional
public class InventoryService {
    
    @Autowired
    private InventoryLogsRepository inventoryLogsRepository;
    
    @Autowired
    private ProductVariantsRepository productVariantsRepository;
    
    public List<InventoryLogs> getAllInventoryLogs() {
        return inventoryLogsRepository.findAll();
    }
    
    public Optional<InventoryLogs> getInventoryLogById(Long id) {
        return inventoryLogsRepository.findById(id);
    }
    
    public List<InventoryLogs> getInventoryLogsByVariantId(Long variantId) {
        return inventoryLogsRepository.findAll().stream()
                .filter(log -> log.getVariantId().equals(variantId))
                .toList();
    }
    
    public InventoryLogs saveInventoryLog(InventoryLogs inventoryLog) {
        if (inventoryLog.getChangedAt() == null) {
            inventoryLog.setChangedAt(new Date());
        }
        return inventoryLogsRepository.save(inventoryLog);
    }
    
    public void deleteInventoryLog(Long id) {
        inventoryLogsRepository.deleteById(id);
    }
    
    public boolean updateStock(Long variantId, Integer newQuantity, String changeType, String reason, Long changedBy) {
        Optional<ProductVariants> variant = productVariantsRepository.findById(variantId);
        if (variant.isPresent()) {
            ProductVariants productVariant = variant.get();
            Integer oldQuantity = productVariant.getQuantityInStock();
            Integer quantityChanged = newQuantity - oldQuantity;
            
            // Cập nhật số lượng tồn kho
            productVariant.setQuantityInStock(newQuantity);
            productVariant.setUpdatedAt(new Date());
            productVariantsRepository.save(productVariant);
            
            // Tạo log inventory
            InventoryLogs log = new InventoryLogs();
            log.setVariantId(variantId);
            log.setChangeType(changeType);
            log.setQuantityBefore(oldQuantity);
            log.setQuantityChanged(quantityChanged);
            log.setQuantityAfter(newQuantity);
            log.setReason(reason);
            log.setChangedBy(changedBy);
            log.setChangedAt(new Date());
            
            saveInventoryLog(log);
            return true;
        }
        return false;
    }
    
    public boolean addStock(Long variantId, Integer quantity, String reason, Long changedBy) {
        Optional<ProductVariants> variant = productVariantsRepository.findById(variantId);
        if (variant.isPresent()) {
            Integer currentStock = variant.get().getQuantityInStock();
            return updateStock(variantId, currentStock + quantity, "stock_in", reason, changedBy);
        }
        return false;
    }
    
    public boolean reduceStock(Long variantId, Integer quantity, String reason, Long changedBy) {
        Optional<ProductVariants> variant = productVariantsRepository.findById(variantId);
        if (variant.isPresent()) {
            Integer currentStock = variant.get().getQuantityInStock();
            if (currentStock >= quantity) {
                return updateStock(variantId, currentStock - quantity, "stock_out", reason, changedBy);
            }
        }
        return false;
    }
    
    public boolean adjustStock(Long variantId, Integer newQuantity, String reason, Long changedBy) {
        return updateStock(variantId, newQuantity, "adjustment", reason, changedBy);
    }
    
    public boolean markDamaged(Long variantId, Integer quantity, String reason, Long changedBy) {
        Optional<ProductVariants> variant = productVariantsRepository.findById(variantId);
        if (variant.isPresent()) {
            Integer currentStock = variant.get().getQuantityInStock();
            if (currentStock >= quantity) {
                return updateStock(variantId, currentStock - quantity, "damaged", reason, changedBy);
            }
        }
        return false;
    }
    
    public boolean returnStock(Long variantId, Integer quantity, String reason, Long changedBy) {
        Optional<ProductVariants> variant = productVariantsRepository.findById(variantId);
        if (variant.isPresent()) {
            Integer currentStock = variant.get().getQuantityInStock();
            return updateStock(variantId, currentStock + quantity, "returned", reason, changedBy);
        }
        return false;
    }
    
    public List<ProductVariants> getLowStockVariants(Integer threshold) {
        return productVariantsRepository.findAll().stream()
                .filter(variant -> variant.getQuantityInStock() != null && variant.getQuantityInStock() <= threshold)
                .filter(variant -> variant.getIsActive() != null && variant.getIsActive())
                .toList();
    }
    
    public List<ProductVariants> getOutOfStockVariants() {
        return productVariantsRepository.findAll().stream()
                .filter(variant -> variant.getQuantityInStock() == null || variant.getQuantityInStock() == 0)
                .filter(variant -> variant.getIsActive() != null && variant.getIsActive())
                .toList();
    }
    
    public Integer getTotalStockByProduct(Long productId) {
        return productVariantsRepository.findAll().stream()
                .filter(variant -> variant.getProductId().equals(productId))
                .filter(variant -> variant.getIsActive() != null && variant.getIsActive())
                .mapToInt(variant -> variant.getQuantityInStock() != null ? variant.getQuantityInStock() : 0)
                .sum();
    }
}