package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.entity.CartItems;
import com.ecommerce.genz_fashion.entity.User;
import com.ecommerce.genz_fashion.service.CartService;
import com.ecommerce.genz_fashion.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {
    
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItems>> getCartItems(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        List<CartItems> cartItems = cartService.getCartItems(currentUser.getId());
        return ResponseEntity.ok(cartItems);
    }
    
    @PostMapping("/add")
    public ResponseEntity<CartItems> addItemToCart(@RequestParam Long productId,
                                                   @RequestParam Integer quantity,
                                                   @AuthenticationPrincipal UserDetailsImpl currentUser) {
        CartItems cartItem = cartService.addItemToCart(currentUser.getId(), productId, quantity);
        return ResponseEntity.ok(cartItem);
    }
    
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItems> updateCartItemQuantity(@PathVariable Long cartItemId,
                                                            @RequestParam Integer quantity,
                                                            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        // Service nên kiểm tra quyền sở hữu của cart item
        CartItems cartItem = cartService.updateCartItemQuantity(currentUser.getId(), cartItemId, quantity);
        if (cartItem == null) {
            // Item đã bị xóa do số lượng <= 0
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cartItem);
    }
    
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartItemId, @AuthenticationPrincipal UserDetailsImpl currentUser) {
        // Service nên kiểm tra quyền sở hữu của cart item
        cartService.removeItemFromCart(currentUser.getId(), cartItemId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        cartService.clearCart(currentUser.getId());
        return ResponseEntity.noContent().build();
    }
    
    // Các endpoint khác như /total, /count, /check nên được tái cấu trúc tương tự
    // để nhận `currentUser` từ @AuthenticationPrincipal và loại bỏ try-catch.
}