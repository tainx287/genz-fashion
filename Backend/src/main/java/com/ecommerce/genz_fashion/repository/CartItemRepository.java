package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Cart;
import com.ecommerce.genz_fashion.entity.CartItem;
import com.ecommerce.genz_fashion.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByCart(Cart cart);
    
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    
    void deleteByCart(Cart cart);
    
    void deleteByCartAndProduct(Cart cart, Product product);
}