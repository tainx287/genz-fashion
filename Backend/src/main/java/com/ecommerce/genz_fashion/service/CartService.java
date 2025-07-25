package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.entity.Cart;
import com.ecommerce.genz_fashion.entity.CartItems;
import com.ecommerce.genz_fashion.entity.Products;
import com.ecommerce.genz_fashion.entity.User;
import com.ecommerce.genz_fashion.repository.CartRepository;
import com.ecommerce.genz_fashion.repository.CartItemRepository;
import com.ecommerce.genz_fashion.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    
    public Cart getOrCreateCart(Long userId) {
        Optional<Cart> existingCart = cartRepository.findByUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        newCart.setCreatedAt(new Date());
        return cartRepository.save(newCart);
    }
    
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
    }
    
    public CartItems addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getCartByUserId(userId);
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        // Check if item already exists in cart
        Optional<CartItems> existingItem = cartItemRepository.findByCartIdAndVariantId(cart.getCartId(), productId);
        
        if (existingItem.isPresent()) {
            CartItems item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice(item.getUnitPrice() * item.getQuantity());
            return cartItemRepository.save(item);
        } else {
            CartItems newItem = new CartItems();
            newItem.setCartId(cart.getCartId());
            newItem.setVariantId(productId);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getBasePrice());
            newItem.setTotalPrice(product.getBasePrice() * quantity);
            newItem.setAddedAt(new Date());
            return cartItemRepository.save(newItem);
        }
    }
    
    public CartItems updateCartItemQuantity(Long cartItemId, Integer quantity) {
        CartItems cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }
        
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getUnitPrice() * quantity);
        return cartItemRepository.save(cartItem);
    }
    
    public void removeItemFromCart(Long cartItemId) {
        CartItems cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItemRepository.delete(cartItem);
    }
    
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cartItemRepository.deleteByCartId(cart.getCartId());
    }
    
    public List<CartItems> getCartItems(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cartItemRepository.findByCartId(cart.getCartId());
    }
    
    public Double getCartTotal(Long userId) {
        List<CartItems> cartItems = getCartItems(userId);
        return cartItems.stream()
                .map(CartItems::getTotalPrice)
                .reduce(0.0, Double::sum);
    }
    
    public int getCartItemCount(Long userId) {
        List<CartItems> cartItems = getCartItems(userId);
        return cartItems.stream()
                .mapToInt(CartItems::getQuantity)
                .sum();
    }
    
    public boolean isProductInCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        return cartItemRepository.findByCartIdAndVariantId(cart.getCartId(), productId).isPresent();
    }
}