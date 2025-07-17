package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Order;
import com.ecommerce.genz_fashion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUserOrderByOrderDateDesc(User user);
    
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
    
    List<Order> findByOrderStatus(Order.OrderStatus status);
    
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC LIMIT :limit")
    List<Order> findTopNByOrderByOrderDateDesc(@Param("limit") int limit);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = 'DELIVERED'")
    BigDecimal calculateTotalRevenue();
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = :status")
    long countByOrderStatus(@Param("status") Order.OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId AND o.orderStatus = :status")
    List<Order> findByUserIdAndOrderStatus(@Param("userId") Long userId, @Param("status") Order.OrderStatus status);
    
    @Query("SELECT EXTRACT(MONTH FROM o.orderDate) as month, SUM(o.totalAmount) as revenue " +
           "FROM Order o WHERE EXTRACT(YEAR FROM o.orderDate) = :year AND o.orderStatus = 'DELIVERED' " +
           "GROUP BY EXTRACT(MONTH FROM o.orderDate) ORDER BY month")
    List<Object[]> getMonthlyRevenue(@Param("year") int year);
}