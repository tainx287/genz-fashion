package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.MemberVouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberVouchersRepository extends JpaRepository<MemberVouchers, Long> {
    
    Optional<MemberVouchers> findByCode(String code);
    
    List<MemberVouchers> findByIsActiveTrue();
    
    List<MemberVouchers> findByDiscountType(String discountType);
    
    @Query("SELECT m FROM MemberVouchers m WHERE m.isActive = true AND m.startDate <= :currentDate AND m.endDate >= :currentDate")
    List<MemberVouchers> findActiveVouchers(@Param("currentDate") Date currentDate);
    
    @Query("SELECT m FROM MemberVouchers m WHERE m.requiredPoints <= :userPoints")
    List<MemberVouchers> findByRequiredPointsLessThanEqual(@Param("userPoints") Integer userPoints);
    
    @Query("SELECT m FROM MemberVouchers m WHERE m.isActive = true AND m.startDate <= :currentDate AND m.endDate >= :currentDate AND m.requiredPoints <= :userPoints")
    List<MemberVouchers> findActiveVouchersForUser(@Param("currentDate") Date currentDate, @Param("userPoints") Integer userPoints);
    
    @Query("SELECT m FROM MemberVouchers m WHERE m.endDate < :currentDate")
    List<MemberVouchers> findExpiredVouchers(@Param("currentDate") Date currentDate);
    
    long countByIsActiveTrue();
    
    @Query("SELECT AVG(m.requiredPoints) FROM MemberVouchers m WHERE m.isActive = true")
    Double getAverageRequiredPoints();
}