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
@Table(name="WishlistItems")
public class WishlistItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistItemId;
    private Long wishlistId;
    private Long productId;
    @Temporal(TemporalType.DATE)
    private Date addedAt;
    
	public WishlistItems() {
		super();
	}

	public WishlistItems(Long wishlistItemId, Long wishlistId, Long productId, Date addedAt) {
		super();
		this.wishlistItemId = wishlistItemId;
		this.wishlistId = wishlistId;
		this.productId = productId;
		this.addedAt = addedAt;
	}

	public Long getWishlistItemId() {
		return wishlistItemId;
	}

	public void setWishlistItemId(Long wishlistItemId) {
		this.wishlistItemId = wishlistItemId;
	}

	public Long getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(Long wishlistId) {
		this.wishlistId = wishlistId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}
    
    
}