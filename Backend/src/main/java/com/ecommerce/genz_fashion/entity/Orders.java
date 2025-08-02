package com.ecommerce.genz_fashion.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Users user;

	@ManyToOne
	@JoinColumn(name = "address_id")
    private Addresses address_id;

    @Temporal(TemporalType.DATE)
    private Date order_date;
    private Double subtotal;
    private Double discount_amount;
    private Double shipping_fee;
    private Double total_amount;

    @Column(name = "order_status")
    private String order_status;

    @Column(name = "payment_method")
    private String payment_method;

    @Column(name = "payment_status")
    private String payment_status;
    private String voucher_code;
    private String notes;
    @Temporal(TemporalType.DATE)
    private Date cancelled_at;
    private String cancelled_reason;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
	@JsonManagedReference
	private List<OrderItems> orderItems;



	public Orders() {
		super();
	}

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
		if (user != null) {
			this.userId = user.getUser_id();
		}
	}

	public Addresses getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Addresses address_id) {
		this.address_id = address_id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Double getShipping_fee() {
		return shipping_fee;
	}

	public void setShipping_fee(Double shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public Double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public String getVoucher_code() {
		return voucher_code;
	}

	public void setVoucher_code(String voucher_code) {
		this.voucher_code = voucher_code;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCancelled_at() {
		return cancelled_at;
	}

	public void setCancelled_at(Date cancelled_at) {
		this.cancelled_at = cancelled_at;
	}

	public String getCancelled_reason() {
		return cancelled_reason;
	}

	public void setCancelled_reason(String cancelled_reason) {
		this.cancelled_reason = cancelled_reason;
	}

	public Orders(Long orderId, Long userId, Users user, Addresses address_id, Date order_date, Double subtotal, Double discount_amount, Double shipping_fee, Double total_amount, String order_status, String payment_method, String payment_status, String voucher_code, String notes, Date cancelled_at, String cancelled_reason, List<OrderItems> orderItems) {
		this.orderId = orderId;
		this.userId = userId;
		this.user = user;
		this.address_id = address_id;
		this.order_date = order_date;
		this.subtotal = subtotal;
		this.discount_amount = discount_amount;
		this.shipping_fee = shipping_fee;
		this.total_amount = total_amount;
		this.order_status = order_status;
		this.payment_method = payment_method;
		this.payment_status = payment_status;
		this.voucher_code = voucher_code;
		this.notes = notes;
		this.cancelled_at = cancelled_at;
		this.cancelled_reason = cancelled_reason;
		this.orderItems = orderItems;
	}
}