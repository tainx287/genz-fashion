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
    
    List<OrderItems> findByVariantId(Long variantId);
    
    @Query("SELECT SUM(oi.quantity) FROM OrderItems oi WHERE oi.orderId = :orderId")
    Integer getTotalQuantityByOrder(@Param("orderId") Long orderId);
    
    @Query("SELECT SUM(oi.totalPrice) FROM OrderItems oi WHERE oi.orderId = :orderId")
    Double getTotalPriceByOrder(@Param("orderId") Long orderId);
    
    @Query("SELECT oi FROM OrderItems oi WHERE oi.variantId = :variantId AND oi.orderId IN (SELECT o.orderId FROM Orders o WHERE o.orderStatus = 'delivered')")
    List<OrderItems> findDeliveredItemsByVariant(@Param("variantId") Long variantId);
    
    long countByOrderId(Long orderId);
    
    long countByVariantId(Long variantId);
}