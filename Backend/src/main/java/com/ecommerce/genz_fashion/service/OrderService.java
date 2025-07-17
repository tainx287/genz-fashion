package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.entity.Order;
import com.ecommerce.genz_fashion.entity.OrderItem;
import com.ecommerce.genz_fashion.entity.User;
import com.ecommerce.genz_fashion.repository.OrderRepository;
import com.ecommerce.genz_fashion.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
    
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }
    
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }
    
    public Page<Order> getOrdersWithPagination(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
    
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(Order.OrderStatus.PENDING);
        return orderRepository.save(order);
    }
    
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setOrderStatus(status);
        if (status == Order.OrderStatus.CANCELLED) {
            order.setCancelledAt(LocalDateTime.now());
        }
        
        return orderRepository.save(order);
    }
    
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setShippingAddress(orderDetails.getShippingAddress());
        order.setNotes(orderDetails.getNotes());
        
        return orderRepository.save(order);
    }
    
    public void cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (order.getOrderStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel delivered order");
        }
        
        order.setOrderStatus(Order.OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        order.setCancelledReason(reason);
        
        orderRepository.save(order);
    }
    
    public BigDecimal calculateOrderTotal(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<Order> getRecentOrders(int limit) {
        return orderRepository.findTopNByOrderByOrderDateDesc(limit);
    }
    
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }
    
    public long getTotalOrdersCount() {
        return orderRepository.count();
    }
    
    public BigDecimal getTotalRevenue() {
        return orderRepository.calculateTotalRevenue();
    }
}