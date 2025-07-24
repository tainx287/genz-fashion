package com.ecommerce.genz_fashion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResponse {
        private Long orderId;
        private Long userId;
        private String userFullName;
        private LocalDateTime orderDate;
        private BigDecimal subtotal;
        private BigDecimal discountAmount;
        private BigDecimal shippingFee;
        private BigDecimal totalAmount;
        private String orderStatus;
        private String paymentMethod;
        private String paymentStatus;
        private String shippingAddress;
        private String notes;
        private List<OrderItemResponse> orderItems;
        private LocalDateTime cancelledAt;
        private String cancelledReason;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long orderItemId;
        private Long productId;
        private String productName;
        private String productImage;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderRequest {
        @NotBlank(message = "Shipping address is required")
        private String shippingAddress;
        
        @NotNull(message = "Payment method is required")
        private String paymentMethod;
        
        private String notes;
        private String voucherCode;
        
        @NotNull(message = "Order items are required")
        private List<OrderItemRequest> orderItems;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;
        
        @NotNull(message = "Quantity is required")
        private Integer quantity;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderStatusRequest {
        @NotNull(message = "Order status is required")
        private String orderStatus;
        
        private String notes;
    }
}