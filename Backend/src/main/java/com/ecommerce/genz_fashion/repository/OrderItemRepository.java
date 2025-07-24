package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
    
    List<OrderItems> findByOrderId(Long orderId);
    
    List<OrderItems> findByProductId(Long productId);
    
    @Query("SELECT oi FROM OrderItems oi WHERE oi.orderId IN (SELECT o.orderId FROM Orders o WHERE o.userId = :userId)")
    List<OrderItems> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT oi.variantId, SUM(oi.quantity) as totalSold " +
           "FROM OrderItems oi JOIN Orders o ON oi.orderId = o.orderId " +
           "WHERE o.orderStatus = 'delivered' " +
           "GROUP BY oi.variantId ORDER BY totalSold DESC")
    List<Object[]> findBestSellingProducts();
    
    @Query("SELECT SUM(oi.quantity) FROM OrderItems oi WHERE oi.variantId = :variantId")
    Long getTotalQuantitySoldByVariant(@Param("variantId") Long variantId);
}