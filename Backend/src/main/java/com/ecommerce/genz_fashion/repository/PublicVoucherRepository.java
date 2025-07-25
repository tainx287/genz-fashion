package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.PublicVouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PublicVoucherRepository extends JpaRepository<PublicVouchers, Long> {
    
    Optional<PublicVouchers> findByCode(String code);
    
    List<PublicVouchers> findByIsActiveTrue();
    
    List<PublicVouchers> findByDiscountType(String discountType);
    
    @Query("SELECT p FROM PublicVouchers p WHERE p.isActive = true AND p.startDate <= :currentDate AND p.endDate >= :currentDate")
    List<PublicVouchers> findActiveVouchers(@Param("currentDate") Date currentDate);
    
    @Query("SELECT p FROM PublicVouchers p WHERE p.usageLimit IS NULL OR p.usedCount < p.usageLimit")
    List<PublicVouchers> findAvailableVouchers();
    
    @Query("SELECT p FROM PublicVouchers p WHERE p.isActive = true AND p.startDate <= :currentDate AND p.endDate >= :currentDate AND (p.usageLimit IS NULL OR p.usedCount < p.usageLimit)")
    List<PublicVouchers> findActiveAndAvailableVouchers(@Param("currentDate") Date currentDate);
    
    @Query("SELECT p FROM PublicVouchers p WHERE p.endDate < :currentDate")
    List<PublicVouchers> findExpiredVouchers(@Param("currentDate") Date currentDate);
    
    long countByIsActiveTrue();
    
    @Query("SELECT SUM(p.usedCount) FROM PublicVouchers p")
    Long getTotalUsageCount();
}