package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Orders;
import com.ecommerce.genz_fashion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    
    List<Orders> findByUserOrderByOrderDateDesc(User user);
    
    List<Orders> findByUserIdOrderByOrderDateDesc(Long userId);
    
    List<Orders> findByOrderStatus(Orders.OrderStatus orderStatus);
    
    @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Orders> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT o FROM Orders o ORDER BY o.orderDate DESC LIMIT :limit")
    List<Orders> findTopNByOrderByOrderDateDesc(@Param("limit") int limit);
    
    @Query("SELECT SUM(o.totalAmount) FROM Orders o WHERE o.orderStatus = 'delivered'")
    BigDecimal calculateTotalRevenue();
    
    List<Orders> findByPaymentStatus(Orders.PaymentStatus paymentStatus);
    
    List<Orders> findByPaymentMethod(Orders.PaymentMethod paymentMethod);
    
    @Query("SELECT o FROM Orders o WHERE o.voucherCode = :voucherCode")
    List<Orders> findByVoucherCode(@Param("voucherCode") String voucherCode);
    
    long countByOrderStatus(Orders.OrderStatus orderStatus);
    
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.userId = :userId")
    long countByUserId(@Param("userId") Long userId);
}