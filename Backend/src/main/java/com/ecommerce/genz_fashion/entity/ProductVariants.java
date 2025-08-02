package com.ecommerce.genz_fashion.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name="product_variants")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVariants {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantId;
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
    private Products product_id;
    private String variantSku;
    private String variantBarcode;

	@ManyToOne
	@JoinColumn(name = "size_id", nullable = false)
    private Sizes size_id;

	@ManyToOne
	@JoinColumn(name = "color_id", nullable = false)
    private Colors color_id;

    private Integer quantityInStock;
    private Double additionalPrice;
    private Double weight;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    
	public ProductVariants() {
		super();
	}

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}

	public Products getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Products product_id) {
		this.product_id = product_id;
	}

	public String getVariantSku() {
		return variantSku;
	}

	public void setVariantSku(String variantSku) {
		this.variantSku = variantSku;
	}

	public String getVariantBarcode() {
		return variantBarcode;
	}

	public void setVariantBarcode(String variantBarcode) {
		this.variantBarcode = variantBarcode;
	}

	public Sizes getSize_id() {
		return size_id;
	}

	public void setSize_id(Sizes size_id) {
		this.size_id = size_id;
	}

	public Colors getColor_id() {
		return color_id;
	}

	public void setColor_id(Colors color_id) {
		this.color_id = color_id;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public Double getAdditionalPrice() {
		return additionalPrice;
	}

	public void setAdditionalPrice(Double additionalPrice) {
		this.additionalPrice = additionalPrice;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
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

	public ProductVariants(Long variantId, Products product_id, String variantSku, String variantBarcode, Sizes size_id, Colors color_id, Integer quantityInStock, Double additionalPrice, Double weight, Boolean isActive, Date createdAt, Date updatedAt) {
		this.variantId = variantId;
		this.product_id = product_id;
		this.variantSku = variantSku;
		this.variantBarcode = variantBarcode;
		this.size_id = size_id;
		this.color_id = color_id;
		this.quantityInStock = quantityInStock;
		this.additionalPrice = additionalPrice;
		this.weight = weight;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}