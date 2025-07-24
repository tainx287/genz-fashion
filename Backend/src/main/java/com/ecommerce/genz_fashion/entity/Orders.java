package com.ecommerce.genz_fashion.entity;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "user_id")
    private Long userId;
    
    // JPA Relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    private Long addressId;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    private Double subtotal;
    private Double discountAmount;
    private Double shippingFee;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    private String voucherCode;
    private String notes;
    @Temporal(TemporalType.DATE)
    private Date cancelledAt;
    private String cancelledReason;
    
	public Orders() {
		super();
	}

	public Orders(Long orderId, Long userId, Long addressId, Date orderDate, Double subtotal, Double discountAmount,
			Double shippingFee, Double totalAmount, String orderStatus, String paymentMethod, String paymentStatus,
			String voucherCode, String notes, Date cancelledAt, String cancelledReason) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.addressId = addressId;
		this.orderDate = orderDate;
		this.subtotal = subtotal;
		this.discountAmount = discountAmount;
		this.shippingFee = shippingFee;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.voucherCode = voucherCode;
		this.notes = notes;
		this.cancelledAt = cancelledAt;
		this.cancelledReason = cancelledReason;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if (user != null) {
			this.userId = user.getUserId();
		}
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(Date cancelledAt) {
		this.cancelledAt = cancelledAt;
	}

	public String getCancelledReason() {
		return cancelledReason;
	}

	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}
    
    public enum OrderStatus {
        pending, confirmed, processing, shipped, delivered, cancelled
    }
    
    public enum PaymentMethod {
        cod, credit_card, paypal, bank_transfer
    }
    
    public enum PaymentStatus {
        unpaid, paid, partial, refunded
    }
}