package com.ecommerce.genz_fashion.entity;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name="OrderItems")
public class OrderItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_item_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "variant_id")
	private ProductVariants variant_id;
    private Integer quantity;
    private Double unit_price;
    private Double total_price;
    
	public OrderItems() {
		super();
	}

	public Long getOrder_item_id() {
		return order_item_id;
	}

	public void setOrder_item_id(Long order_item_id) {
		this.order_item_id = order_item_id;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public ProductVariants getVariant_id() {
		return variant_id;
	}

	public void setVariant_id(ProductVariants variant_id) {
		this.variant_id = variant_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public OrderItems(Long order_item_id, Orders order, ProductVariants variant_id, Integer quantity, Double unit_price, Double total_price) {
		this.order_item_id = order_item_id;
		this.order = order;
		this.variant_id = variant_id;
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.total_price = total_price;
	}
}