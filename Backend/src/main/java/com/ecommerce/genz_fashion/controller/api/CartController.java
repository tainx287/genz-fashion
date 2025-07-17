package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.entity.CartItem;
import com.ecommerce.genz_fashion.entity.User;
import com.ecommerce.genz_fashion.service.AuthService;
import com.ecommerce.genz_fashion.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {
    
    private final CartService cartService;
    private final AuthService authService;
    
    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems() {
        User currentUser = authService.getCurrentUser()
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> cartItems = cartService.getCartItems(currentUser.getUserId());
        return ResponseEntity.ok(cartItems);
    }
    
    @PostMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            User currentUser = authService.getCurrentUser()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            CartItem cartItem = cartService.addItemToCart(currentUser.getUserId(), productId, quantity);
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long cartItemId, @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartService.updateCartItemQuantity(cartItemId, quantity);
            if (cartItem == null) {
                return ResponseEntity.ok().build(); // Item was removed due to quantity 0
            }
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartItemId) {
        try {
            cartService.removeItemFromCart(cartItemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        try {
            User currentUser = authService.getCurrentUser()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            cartService.clearCart(currentUser.getUserId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getCartTotal() {
        try {
            User currentUser = authService.getCurrentUser()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            BigDecimal total = cartService.getCartTotal(currentUser.getUserId());
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getCartItemCount() {
        try {
            User currentUser = authService.getCurrentUser()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            int count = cartService.getCartItemCount(currentUser.getUserId());
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> isProductInCart(@PathVariable Long productId) {
        try {
            User currentUser = authService.getCurrentUser()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            boolean inCart = cartService.isProductInCart(currentUser.getUserId(), productId);
            return ResponseEntity.ok(inCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}