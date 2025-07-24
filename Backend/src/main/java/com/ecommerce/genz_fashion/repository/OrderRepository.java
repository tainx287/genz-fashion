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
    
    List<Orders> findByOrderStatus(Orders.OrderStatus status);
    
    List<Orders> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT o FROM Orders o ORDER BY o.orderDate DESC LIMIT :limit")
    List<Orders> findTopNByOrderByOrderDateDesc(@Param("limit") int limit);
    
    @Query("SELECT SUM(o.totalAmount) FROM Orders o WHERE o.orderStatus = 'DELIVERED'")
    BigDecimal calculateTotalRevenue();
    
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderStatus = :status")
    long countByOrderStatus(@Param("status") Orders.OrderStatus status);
    
    @Query("SELECT o FROM Orders o WHERE o.user.userId = :userId AND o.orderStatus = :status")
    List<Orders> findByUserIdAndOrderStatus(@Param("userId") Long userId, @Param("status") Orders.OrderStatus status);
    
    @Query("SELECT EXTRACT(MONTH FROM o.orderDate) as month, SUM(o.totalAmount) as revenue " +
           "FROM Orders o WHERE EXTRACT(YEAR FROM o.orderDate) = :year AND o.orderStatus = 'DELIVERED' " +
           "GROUP BY EXTRACT(MONTH FROM o.orderDate) ORDER BY month")
    List<Object[]> getMonthlyRevenue(@Param("year") int year);
}