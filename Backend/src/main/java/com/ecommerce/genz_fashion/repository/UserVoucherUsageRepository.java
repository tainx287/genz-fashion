package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.UserVoucherUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserVoucherUsageRepository extends JpaRepository<UserVoucherUsage, Long> {
    
    List<UserVoucherUsage> findByUserId(Long userId);
    
    List<UserVoucherUsage> findByVoucherTypeAndVoucherId(String voucherType, Long voucherId);
    
    List<UserVoucherUsage> findByOrderId(Long orderId);
    
    @Query("SELECT u FROM UserVoucherUsage u WHERE u.userId = :userId AND u.voucherType = :voucherType AND u.voucherId = :voucherId")
    List<UserVoucherUsage> findByUserIdAndVoucherTypeAndVoucherId(@Param("userId") Long userId, @Param("voucherType") String voucherType, @Param("voucherId") Long voucherId);
    
    @Query("SELECT u FROM UserVoucherUsage u WHERE u.usedAt BETWEEN :startDate AND :endDate ORDER BY u.usedAt DESC")
    List<UserVoucherUsage> findByUsedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT u FROM UserVoucherUsage u WHERE u.userId = :userId AND u.usedAt BETWEEN :startDate AND :endDate")
    List<UserVoucherUsage> findByUserIdAndUsedAtBetween(@Param("userId") Long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    long countByUserId(Long userId);
    
    long countByVoucherTypeAndVoucherId(String voucherType, Long voucherId);
    
    @Query("SELECT COUNT(u) FROM UserVoucherUsage u WHERE u.userId = :userId AND u.voucherType = :voucherType AND u.voucherId = :voucherId")
    long countByUserIdAndVoucherTypeAndVoucherId(@Param("userId") Long userId, @Param("voucherType") String voucherType, @Param("voucherId") Long voucherId);
}