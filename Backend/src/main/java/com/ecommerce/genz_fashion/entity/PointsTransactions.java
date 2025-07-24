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
@Table(name="PointsTransactions")
public class PointsTransactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long userId;
    private String transactionType;
    private Integer points;
    private String description;
    private String referenceType;
    private Long referenceId;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
	public PointsTransactions() {
		super();
	}

	public PointsTransactions(Long transactionId, Long userId, String transactionType, Integer points,
			String description, String referenceType, Long referenceId, Date createdAt) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.transactionType = transactionType;
		this.points = points;
		this.description = description;
		this.referenceType = referenceType;
		this.referenceId = referenceId;
		this.createdAt = createdAt;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
}