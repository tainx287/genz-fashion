package com.ecommerce.genz_fashion.controller;


import com.ecommerce.genz_fashion.dto.OrderDTO;
import com.ecommerce.genz_fashion.entity.Orders;
import com.ecommerce.genz_fashion.service.orders.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrdersService orderService;


    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Lấy chi tiết 1 đơn hàng
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = orderService.getOrderWithItems(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderDTO orderDTO) {
        System.out.println("Created Order: " + orderDTO.getPaymentMethod());
        Orders createdOrder = orderService.createOrderFromDTO(orderDTO);

        return ResponseEntity.ok(createdOrder);
    }

    // Cập nhật đơn hàng
    @PostMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
        Orders updated = orderService.updateOrder(id, updatedOrder);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
