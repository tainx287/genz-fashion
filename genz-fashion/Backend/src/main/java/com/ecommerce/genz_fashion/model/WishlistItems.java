package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="WishlistItems")
public class WishlistItems {
	@Id
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