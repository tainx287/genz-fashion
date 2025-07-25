package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long> {
    
    List<Payments> findByOrderId(Long orderId);
    
    List<Payments> findByPaymentMethod(String paymentMethod);
    
    List<Payments> findByPaymentStatus(String paymentStatus);
    
    @Query("SELECT p FROM Payments p WHERE p.paymentDate BETWEEN :startDate AND :endDate ORDER BY p.paymentDate DESC")
    List<Payments> findByPaymentDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT p FROM Payments p WHERE p.orderId = :orderId AND p.paymentStatus = :status")
    List<Payments> findByOrderIdAndPaymentStatus(@Param("orderId") Long orderId, @Param("status") String status);
    
    @Query("SELECT SUM(p.amount) FROM Payments p WHERE p.paymentStatus = 'completed'")
    Double getTotalCompletedPayments();
    
    @Query("SELECT SUM(p.amount) FROM Payments p WHERE p.orderId = :orderId AND p.paymentStatus = 'completed'")
    Double getTotalCompletedPaymentsByOrder(@Param("orderId") Long orderId);
    
    long countByPaymentStatus(String paymentStatus);
    
    long countByPaymentMethod(String paymentMethod);
    
    @Query("SELECT p FROM Payments p ORDER BY p.paymentDate DESC")
    List<Payments> findAllOrderByPaymentDateDesc();
}