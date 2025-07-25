package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.InventoryLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InventoryLogsRepository extends JpaRepository<InventoryLogs, Long> {
    
    List<InventoryLogs> findByVariantId(Long variantId);
    
    List<InventoryLogs> findByChangeType(String changeType);
    
    List<InventoryLogs> findByChangedBy(Long changedBy);
    
    @Query("SELECT i FROM InventoryLogs i WHERE i.changedAt BETWEEN :startDate AND :endDate ORDER BY i.changedAt DESC")
    List<InventoryLogs> findByChangedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT i FROM InventoryLogs i WHERE i.variantId = :variantId AND i.changedAt BETWEEN :startDate AND :endDate ORDER BY i.changedAt DESC")
    List<InventoryLogs> findByVariantIdAndChangedAtBetween(@Param("variantId") Long variantId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT i FROM InventoryLogs i ORDER BY i.changedAt DESC")
    List<InventoryLogs> findAllOrderByChangedAtDesc();
    
    long countByVariantId(Long variantId);
    
    long countByChangeType(String changeType);
    
    @Query("SELECT i FROM InventoryLogs i WHERE i.changedAt < :beforeDate")
    List<InventoryLogs> findByChangedAtBefore(@Param("beforeDate") Date beforeDate);
}