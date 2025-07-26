package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.dto.OrderDto;
import com.ecommerce.genz_fashion.entity.Orders;
import com.ecommerce.genz_fashion.mapper.OrderMapper;
import com.ecommerce.genz_fashion.service.OrderService;
import com.ecommerce.genz_fashion.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final OrderMapper orderMapper;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<OrderDto.OrderResponse>> getAllOrders() {
        List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orderMapper.toOrderResponses(orders));
    }
    
    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<OrderDto.OrderResponse>> getOrdersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> orderPage = orderService.getOrdersWithPagination(pageable);
        return ResponseEntity.ok(orderPage.map(orderMapper::toOrderResponse));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto.OrderResponse> getOrderById(@PathVariable Long id) {
        // Service sẽ ném ResourceNotFoundException nếu không tìm thấy
        Orders order = orderService.getOrderById(id).orElseThrow(() -> new com.ecommerce.genz_fashion.exception.ResourceNotFoundException("Order not found"));
        return ResponseEntity.ok(orderMapper.toOrderResponse(order));
    }
    
    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<OrderDto.OrderResponse>> getMyOrders(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        List<Orders> orders = orderService.getOrdersByUserId(currentUser.getId());
        return ResponseEntity.ok(orderMapper.toOrderResponses(orders));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDto.OrderResponse> createOrder(@Valid @RequestBody OrderDto.CreateOrderRequest request,
                                                              @AuthenticationPrincipal UserDetailsImpl currentUser) {
        // Logic tạo order từ giỏ hàng sẽ nằm trong service
        Orders createdOrder = orderService.createOrderFromCart(currentUser.getId(), request);
        return ResponseEntity.ok(orderMapper.toOrderResponse(createdOrder));
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<OrderDto.OrderResponse> updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderDto.UpdateOrderStatusRequest request) {
        Orders updatedOrder = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(orderMapper.toOrderResponse(updatedOrder));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, @RequestParam(required = false) String reason) {
        // Logic nên kiểm tra xem người dùng có quyền hủy đơn hàng này không
        orderService.cancelOrder(id, reason);
        return ResponseEntity.ok(new com.ecommerce.genz_fashion.payload.response.MessageResponse("Order cancelled successfully."));
    }
    
    // Các endpoint khác như getOrdersByUserId, getOrdersByStatus, getRecentOrders, statistics...
    // cũng nên được tái cấu trúc để trả về DTO thay vì Entity.
}