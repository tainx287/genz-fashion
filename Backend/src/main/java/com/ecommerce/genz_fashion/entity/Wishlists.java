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
@Table(name="Wishlists")
public class Wishlists {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;
    private Long userId;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
	public Wishlists() {
		super();
	}

	public Wishlists(Long wishlistId, Long userId, Date createdAt) {
		super();
		this.wishlistId = wishlistId;
		this.userId = userId;
		this.createdAt = createdAt;
	}

	public Long getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Long wishlistId) {
		this.wishlistId = wishlistId;
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
    
}