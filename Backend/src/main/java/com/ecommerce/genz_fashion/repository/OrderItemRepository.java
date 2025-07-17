package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    List<OrderItem> findByOrderId(Long orderId);
    
    List<OrderItem> findByProductId(Long productId);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.user.userId = :userId")
    List<OrderItem> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT oi.product.productId, SUM(oi.quantity) as totalSold " +
           "FROM OrderItem oi WHERE oi.order.orderStatus = 'DELIVERED' " +
           "GROUP BY oi.product.productId ORDER BY totalSold DESC")
    List<Object[]> findBestSellingProducts();
    
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.productId = :productId")
    Long getTotalQuantitySoldByProduct(@Param("productId") Long productId);
}