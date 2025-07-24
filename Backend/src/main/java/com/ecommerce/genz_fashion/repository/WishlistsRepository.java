package com.ecommerce.genz_fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.genz_fashion.entity.Wishlists;

@Repository
public interface WishlistsRepository extends JpaRepository<Wishlists, Long> {
    
}