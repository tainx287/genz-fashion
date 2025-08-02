package com.ecommerce.genz_fashion.service.orders;


import com.ecommerce.genz_fashion.dto.OrderDTO;
import com.ecommerce.genz_fashion.dto.OrderItemDTO;
import com.ecommerce.genz_fashion.entity.Addresses;
import com.ecommerce.genz_fashion.entity.ProductVariants;
import com.ecommerce.genz_fashion.repository.AddressRepository;
import com.ecommerce.genz_fashion.repository.orders.OrderItemRepository;
import com.ecommerce.genz_fashion.repository.orders.OrderRepository;
import com.ecommerce.genz_fashion.repository.product.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.genz_fashion.entity.Orders;
import com.ecommerce.genz_fashion.entity.OrderItems;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private VariantRepository variantRepo;

    @Autowired
    private AddressRepository addressesRepository;
    @Autowired
    private VariantRepository variantRepository;


    public List<OrderDTO> getAllOrdersWithItemsDTO() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Orders order : orders) {
            // Lấy items theo orderId
            List<OrderItems> items = orderItemRepository.findByOrderId(order.getOrderId());
            order.setOrderItems(items);

            // Map OrderItems -> OrderItemDTO
            List<OrderItemDTO> itemDTOs = items.stream().map(item -> {
                OrderItemDTO dto = new OrderItemDTO();
                dto.setVariantId(item.getVariant_id().getVariantId());
                dto.setQuantity(item.getQuantity());
                dto.setUnitPrice(item.getUnit_price());
                return dto;
            }).toList();

            // Map Orders -> OrderDTO
            OrderDTO dto = new OrderDTO();
            dto.setUserId(order.getUserId());
            dto.setAddressId(order.getAddress_id() != null ? order.getAddress_id().getAddress_id() : null);
            dto.setOrderDate(order.getOrder_date());
            dto.setSubtotal(order.getSubtotal());
            dto.setDiscountAmount(order.getDiscount_amount());
            dto.setShippingFee(order.getShipping_fee());
            dto.setTotalAmount(order.getTotal_amount());
            dto.setOrderStatus(order.getOrder_status());
            dto.setPaymentMethod(order.getPayment_method());
            dto.setPaymentStatus(order.getPayment_status());
            dto.setVoucherCode(order.getVoucher_code());
            dto.setNotes(order.getNotes());
            dto.setOrderItems(itemDTOs);

            orderDTOList.add(dto);
        }

        return orderDTOList;
    }

    public Orders createOrderFromDTO(OrderDTO dto) {
        Orders order = new Orders();

        // Gán thông tin cơ bản
        order.setUserId(dto.getUserId());
        order.setOrder_date(dto.getOrderDate());
        order.setSubtotal(dto.getSubtotal());
        order.setDiscount_amount(dto.getDiscountAmount());
        order.setShipping_fee(dto.getShippingFee());
        order.setTotal_amount(dto.getTotalAmount());

        // Chuyển String -> Enum (case-insensitive)
        if (dto.getOrderStatus() != null) {
            order.setOrder_status(dto.getOrderStatus());          // OK
        }

        if (dto.getPaymentMethod() != null) {
            order.setPayment_method(dto.getPaymentMethod());      // OK
        }

        if (dto.getPaymentStatus() != null) {
            order.setPayment_status(dto.getPaymentStatus());      // OK
        }

        order.setVoucher_code(dto.getVoucherCode());
        order.setNotes(dto.getNotes());

        System.out.println("Payment method in DTO: " + dto.getPaymentMethod());
        System.out.println("Mapped enum: " + order.getPayment_method());


        // Gán địa chỉ nếu có
        if (dto.getAddressId() != null) {
            Addresses address = addressesRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy địa chỉ với id: " + dto.getAddressId()));
            order.setAddress_id(address);
        }

        // Lưu order trước để có orderId
        Orders savedOrder = orderRepository.save(order);

        // Gán các item
        List<OrderItems> orderItemsList = new ArrayList<>();
        for (OrderItemDTO itemDTO : dto.getOrderItems()) {
            ProductVariants variant = variantRepository.findById(itemDTO.getVariantId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy variant với id: " + itemDTO.getVariantId()));

            OrderItems item = new OrderItems();
            item.setOrder(savedOrder);
            item.setVariant_id(variant);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnit_price(itemDTO.getUnitPrice());
            item.setTotal_price(itemDTO.getUnitPrice() * itemDTO.getQuantity());

            orderItemsList.add(item);
        }

        orderItemRepository.saveAll(orderItemsList);
        savedOrder.setOrderItems(orderItemsList);

        return savedOrder;
    }


    public Orders createOrder(Orders order, List<OrderItems> orderItems) {
        for (OrderItems item : orderItems) {
            item.setOrder(order); // Gán quan hệ đúng
            // 🚨 TẠI ĐÂY mới là phần bạn đang thiếu:
            Long variantId = item.getVariant_id().getVariantId();
            ProductVariants variant = variantRepo.findById(variantId)
                    .orElseThrow(() -> new RuntimeException("Variant not found: " + variantId));
            item.setVariant_id(variant); // 🧠 set lại entity thật
        }
        order.setOrderItems(orderItems); // <-- RẤT QUAN TRỌNG
        return orderRepository.save(order); // Hibernate sẽ cascade
    }

    public Orders getOrderWithItems(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<OrderItems> items = orderItemRepository.findByOrderId(orderId);
            order.setOrderItems(items); // cần getter/setter cho orderItems
        }
        return order;
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
