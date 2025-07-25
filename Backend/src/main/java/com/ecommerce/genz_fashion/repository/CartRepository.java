package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    Optional<Cart> findByUserId(Long userId);
    
    List<Cart> findByCreatedAtBetween(Date startDate, Date endDate);
    
    @Query("SELECT c FROM Cart c WHERE c.updatedAt < :beforeDate")
    List<Cart> findInactiveCarts(@Param("beforeDate") Date beforeDate);
    
    long countByUserId(Long userId);
    
    boolean existsByUserId(Long userId);
    
    @Query("SELECT c FROM Cart c ORDER BY c.updatedAt DESC")
    List<Cart> findAllOrderByUpdatedAtDesc();
}