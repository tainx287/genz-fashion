package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Reviews")
public class Reviews {
	@Id
    private Long reviewId;
    private Long productId;
    private Long userId;
    private Long orderId;
    private Integer rating;
    private String comment;
    private Boolean isVerifiedPurchase;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    
	public Reviews() {
		super();
	}

	public Reviews(Long reviewId, Long productId, Long userId, Long orderId, Integer rating, String comment,
			Boolean isVerifiedPurchase, Date createdAt, Date updatedAt) {
		super();
		this.reviewId = reviewId;
		this.productId = productId;
		this.userId = userId;
		this.orderId = orderId;
		this.rating = rating;
		this.comment = comment;
		this.isVerifiedPurchase = isVerifiedPurchase;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getIsVerifiedPurchase() {
		return isVerifiedPurchase;
	}

	public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) {
		this.isVerifiedPurchase = isVerifiedPurchase;
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