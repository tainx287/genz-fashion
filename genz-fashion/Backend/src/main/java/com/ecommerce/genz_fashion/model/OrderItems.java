package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OrderItems")
public class OrderItems {
	@Id
    private Long orderItemId;
    private Long orderId;
    private Long variantId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    
	public OrderItems() {
		super();
	}

	public OrderItems(Long orderItemId, Long orderId, Long variantId, Integer quantity, Double unitPrice,
			Double totalPrice) {
		super();
		this.orderItemId = orderItemId;
		this.orderId = orderId;
		this.variantId = variantId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
    
    
}