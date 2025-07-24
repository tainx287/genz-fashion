package com.ecommerce.genz_fashion.entity;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="CartItems")
public class CartItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private Long cartId;
    private Long variantId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    @Temporal(TemporalType.DATE)
    private Date addedAt;
    
    
    
	public CartItems() {
		super();
	}



	public CartItems(Long cartItemId, Long cartId, Long variantId, Integer quantity, Double unitPrice,
			Double totalPrice, Date addedAt) {
		super();
		this.cartItemId = cartItemId;
		this.cartId = cartId;
		this.variantId = variantId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.addedAt = addedAt;
	}



	public Long getCartItemId() {
		return cartItemId;
	}



	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}



	public Long getCartId() {
		return cartId;
	}



	public void setCartId(Long cartId) {
		this.cartId = cartId;
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



	public Date getAddedAt() {
		return addedAt;
	}



	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}
    
    
	
}