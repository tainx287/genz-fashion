package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Cart")
public class Cart {
	@Id
    private Long cartId;
    private Long userId;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    
    
    
	public Cart() {
		super();
	}



	public Cart(Long cartId, Long userId, Date createdAt, Date updatedAt) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



	public Long getCartId() {
		return cartId;
	}



	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    
    
}