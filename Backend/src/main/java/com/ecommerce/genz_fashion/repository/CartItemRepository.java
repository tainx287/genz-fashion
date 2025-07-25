package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    
    List<CartItems> findByCartId(Long cartId);
    
    Optional<CartItems> findByCartIdAndVariantId(Long cartId, Long variantId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItems c WHERE c.cartId = :cartId")
    void deleteByCartId(@Param("cartId") Long cartId);
    
    @Query("SELECT SUM(c.totalPrice) FROM CartItems c WHERE c.cartId = :cartId")
    Double getTotalByCartId(@Param("cartId") Long cartId);
    
    @Query("SELECT SUM(c.quantity) FROM CartItems c WHERE c.cartId = :cartId")
    Integer getTotalQuantityByCartId(@Param("cartId") Long cartId);
    
    long countByCartId(Long cartId);
    
    boolean existsByCartIdAndVariantId(Long cartId, Long variantId);
}