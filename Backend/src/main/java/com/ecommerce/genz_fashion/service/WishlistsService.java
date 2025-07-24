package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.genz_fashion.entity.Wishlists;
import com.ecommerce.genz_fashion.repository.WishlistsRepository;

@Service
public class WishlistsService {
    
    @Autowired
    private WishlistsRepository wishlistsRepository;
    
    public List<Wishlists> getAllWishlists() {
        return wishlistsRepository.findAll();
    }
    
    public Optional<Wishlists> getWishlistById(Long id) {
        return wishlistsRepository.findById(id);
    }
    
    public Wishlists saveWishlist(Wishlists wishlist) {
        if (wishlist.getCreatedAt() == null) {
            wishlist.setCreatedAt(new Date());
        }
        return wishlistsRepository.save(wishlist);
    }
    
    public void deleteWishlist(Long id) {
        wishlistsRepository.deleteById(id);
    }
    
    public Optional<Wishlists> getWishlistByUserId(Long userId) {
        return wishlistsRepository.findAll().stream()
                .filter(wishlist -> wishlist.getUserId().equals(userId))
                .findFirst();
    }
    
    public Wishlists createWishlistForUser(Long userId) {
        Wishlists wishlist = new Wishlists();
        wishlist.setUserId(userId);
        wishlist.setCreatedAt(new Date());
        return saveWishlist(wishlist);
    }
}