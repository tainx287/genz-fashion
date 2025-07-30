package com.ecommerce.genz_fashion.service.orders;


import com.ecommerce.genz_fashion.repository.orders.OrderItemRepository;
import com.ecommerce.genz_fashion.repository.orders.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.genz_fashion.entity.Orders;
import com.ecommerce.genz_fashion.entity.OrderItems;
import java.util.Optional;
import java.util.List;

@Service
public class OrdersService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    // Create a new order
    public Orders createOrder(Orders order, List<OrderItems> orderItems) {
        // Save the order first to generate an ID
        order = orderRepository.save(order);
        // Set the order ID for each order item
        for (OrderItems item : orderItems) {
            item.setOrder_item_id(order.getOrderId());
            orderItemRepository.save(item);
        }
        // Return the saved order with its items

        return order;
    }

    public Orders getOrderWithItems(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<OrderItems> items = orderItemRepository.findByOrderId(orderId);
            order.setOrderItems(items); // cần getter/setter cho orderItems
        }
        return order;
    }

    public List<Orders> getAllOrdersWithItems() {
        List<Orders> orders = orderRepository.findAll();
        for (Orders order : orders) {
            List<OrderItems> items = orderItemRepository.findByOrderId(order.getOrderId());
            order.setOrderItems(items);
        }
        return orders;
    }


    // Get an order by ID
    public Optional<Orders> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Get all orders
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update an existing order
    public Orders updateOrder(Long orderId, Orders updatedOrder) {
        Optional<Orders> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isPresent()) {
            Orders order = existingOrder.get();
            order.setUserId(updatedOrder.getUserId());
            order.setAddress_id(updatedOrder.getAddress_id());
            order.setOrder_date(updatedOrder.getOrder_date());
            order.setSubtotal(updatedOrder.getSubtotal());
            order.setDiscount_amount(updatedOrder.getDiscount_amount());
            order.setShipping_fee(updatedOrder.getShipping_fee());
            order.setTotal_amount(updatedOrder.getTotal_amount());
            order.setOrder_status(updatedOrder.getOrder_status());
            order.setPayment_method(updatedOrder.getPayment_method());
            order.setPayment_status(updatedOrder.getPayment_status());
            order.setVoucher_code(updatedOrder.getVoucher_code());
            order.setNotes(updatedOrder.getNotes());
            order.setCancelled_at(updatedOrder.getCancelled_at());
            order.setCancelled_reason(updatedOrder.getCancelled_reason());
            return orderRepository.save(order);
        }
        return null;
    }


}
