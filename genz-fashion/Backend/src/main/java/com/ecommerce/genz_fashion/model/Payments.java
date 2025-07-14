package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Payments")
public class Payments {
	@Id
    private Long paymentId;
    private Long orderId;
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
    private String gatewayResponse;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
	public Payments() {
		super();
	}

	public Payments(Long paymentId, Long orderId, Date paymentDate, Double amount, String paymentMethod,
			String paymentStatus, String transactionId, String gatewayResponse, Date createdAt) {
		super();
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
		this.gatewayResponse = gatewayResponse;
		this.createdAt = createdAt;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getGatewayResponse() {
		return gatewayResponse;
	}

	public void setGatewayResponse(String gatewayResponse) {
		this.gatewayResponse = gatewayResponse;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
    
}