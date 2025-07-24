package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Cart;
import com.ecommerce.genz_fashion.entity.CartItems;
import com.ecommerce.genz_fashion.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    
    List<CartItems> findByCart(Cart cart);
    
    Optional<CartItems> findByCartAndProduct(Cart cart, Products product);
    
    void deleteByCart(Cart cart);
    
    void deleteByCartAndProduct(Cart cart, Products product);
}