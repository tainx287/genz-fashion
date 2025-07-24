package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.entity.Orders;
import com.ecommerce.genz_fashion.entity.User;
import com.ecommerce.genz_fashion.service.AuthService;
import com.ecommerce.genz_fashion.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {
    
    private final OrderService orderService;
    private final AuthService authService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<Orders>> getOrdersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> orders = orderService.getOrdersWithPagination(pageable);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok().body(order))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Orders>> getMyOrders() {
        User currentUser = authService.getCurrentUser()
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Orders> orders = orderService.getOrdersByUser(currentUser);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable Long userId) {
        List<Orders> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@PathVariable Orders.OrderStatus status) {
        List<Orders> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody Orders order) {
        try {
            User currentUser = authService.getCurrentUser()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(currentUser);
            Orders createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @Valid @RequestBody Orders orderDetails) {
        try {
            Orders updatedOrder = orderService.updateOrder(id, orderDetails);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long id, @RequestParam Orders.OrderStatus status) {
        try {
            Orders updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, @RequestParam(required = false) String reason) {
        try {
            orderService.cancelOrder(id, reason);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> getOrderTotal(@PathVariable Long id) {
        try {
            BigDecimal total = orderService.calculateOrderTotal(id);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/recent")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Orders>> getRecentOrders(@RequestParam(defaultValue = "10") int limit) {
        List<Orders> orders = orderService.getRecentOrders(limit);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/statistics/total-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalOrdersCount() {
        long count = orderService.getTotalOrdersCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/statistics/total-revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        BigDecimal revenue = orderService.getTotalRevenue();
        return ResponseEntity.ok(revenue);
    }
}