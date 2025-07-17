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
@Table(name="Shipping")
public class Shipping {
	@Id
    private Long shippingId;
    private Long orderId;
    private String shippingMethod;
    private String trackingNumber;
    private String shippingStatus;
    private Double shippingFee;
    @Temporal(TemporalType.DATE)
    private Date estimatedDeliveryDate;
    @Temporal(TemporalType.DATE)
    private Date shippedAt;
    private String notes;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
	public Shipping() {
		super();
	}

	public Shipping(Long shippingId, Long orderId, String shippingMethod, String trackingNumber, String shippingStatus,
			Double shippingFee, Date estimatedDeliveryDate, Date shippedAt, String notes, Date createdAt) {
		super();
		this.shippingId = shippingId;
		this.orderId = orderId;
		this.shippingMethod = shippingMethod;
		this.trackingNumber = trackingNumber;
		this.shippingStatus = shippingStatus;
		this.shippingFee = shippingFee;
		this.estimatedDeliveryDate = estimatedDeliveryDate;
		this.shippedAt = shippedAt;
		this.notes = notes;
		this.createdAt = createdAt;
	}

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Date getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	public Date getShippedAt() {
		return shippedAt;
	}

	public void setShippedAt(Date shippedAt) {
		this.shippedAt = shippedAt;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
}