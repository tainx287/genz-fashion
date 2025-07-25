package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    
    Optional<Shipping> findByOrderId(Long orderId);
    
    List<Shipping> findByShippingStatus(String shippingStatus);
    
    List<Shipping> findByShippingMethod(String shippingMethod);
    
    List<Shipping> findByTrackingNumber(String trackingNumber);
    
    @Query("SELECT s FROM Shipping s WHERE s.shippedAt BETWEEN :startDate AND :endDate ORDER BY s.shippedAt DESC")
    List<Shipping> findByShippedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT s FROM Shipping s WHERE s.estimatedDeliveryDate BETWEEN :startDate AND :endDate")
    List<Shipping> findByEstimatedDeliveryDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT s FROM Shipping s ORDER BY s.createdAt DESC")
    List<Shipping> findAllOrderByCreatedAtDesc();
    
    long countByShippingStatus(String shippingStatus);
    
    long countByShippingMethod(String shippingMethod);
    
    @Query("SELECT SUM(s.shippingFee) FROM Shipping s WHERE s.shippingStatus = 'delivered'")
    Double getTotalShippingRevenue();
    
    @Query("SELECT s FROM Shipping s WHERE s.estimatedDeliveryDate < :currentDate AND s.shippingStatus NOT IN ('delivered', 'failed')")
    List<Shipping> findOverdueShipments(@Param("currentDate") Date currentDate);
}