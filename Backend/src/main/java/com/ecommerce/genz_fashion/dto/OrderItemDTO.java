package com.ecommerce.genz_fashion.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long variantId;
    private int quantity;
    private Double unitPrice;

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long variantId, int quantity, Double unitPrice) {
        this.variantId = variantId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}