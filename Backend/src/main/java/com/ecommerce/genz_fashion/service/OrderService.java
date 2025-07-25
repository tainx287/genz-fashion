package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.entity.Orders;
import com.ecommerce.genz_fashion.entity.OrderItems;
import com.ecommerce.genz_fashion.entity.User;
import com.ecommerce.genz_fashion.repository.OrderRepository;
import com.ecommerce.genz_fashion.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Orders> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Orders> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
    
    public List<Orders> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }
    
    public List<Orders> getOrdersByStatus(Orders.OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }
    
    public Page<Orders> getOrdersWithPagination(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
    
    public Orders createOrder(Orders order) {
        order.setOrderDate(new Date());
        order.setOrderStatus(Orders.OrderStatus.pending);
        return orderRepository.save(order);
    }
    
    public Orders updateOrderStatus(Long orderId, Orders.OrderStatus status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setOrderStatus(status);
        if (status == Orders.OrderStatus.cancelled) {
            order.setCancelledAt(new Date());
        }
        
        return orderRepository.save(order);
    }
    
    public Orders updateOrder(Long id, Orders orderDetails) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setAddressId(orderDetails.getAddressId());
        order.setNotes(orderDetails.getNotes());
        
        return orderRepository.save(order);
    }
    
    public void cancelOrder(Long orderId, String reason) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (order.getOrderStatus() == Orders.OrderStatus.delivered) {
            throw new RuntimeException("Cannot cancel delivered order");
        }
        
        order.setOrderStatus(Orders.OrderStatus.cancelled);
        order.setCancelledAt(new Date());
        order.setCancelledReason(reason);
        
        orderRepository.save(order);
    }
    
    public BigDecimal calculateOrderTotal(Long orderId) {
        List<OrderItems> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(item -> BigDecimal.valueOf(item.getUnitPrice() * item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<Orders> getRecentOrders(int limit) {
        return orderRepository.findTopNByOrderByOrderDateDesc(limit);
    }
    
    public List<Orders> getOrdersByDateRange(Date startDate, Date endDate) {
        // Convert Date to LocalDateTime for repository method compatibility
        java.time.LocalDateTime startDateTime = startDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();
        java.time.LocalDateTime endDateTime = endDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();
        return orderRepository.findByOrderDateBetween(startDateTime, endDateTime);
    }
    
    public long getTotalOrdersCount() {
        return orderRepository.count();
    }
    
    public BigDecimal getTotalRevenue() {
        return orderRepository.calculateTotalRevenue();
    }
}