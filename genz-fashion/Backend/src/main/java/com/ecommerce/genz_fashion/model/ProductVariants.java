package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ProductVariants")
public class ProductVariants {
	@Id
    private Long variantId;
    private Long productId;
    private String variantSku;
    private String variantBarcode;
    private Long sizeId;
    private Long colorId;
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

	public ProductVariants(Long variantId, Long productId, String variantSku, String variantBarcode, Long sizeId,
			Long colorId, Integer quantityInStock, Double additionalPrice, Double weight, Boolean isActive,
			Date createdAt, Date updatedAt) {
		super();
		this.variantId = variantId;
		this.productId = productId;
		this.variantSku = variantSku;
		this.variantBarcode = variantBarcode;
		this.sizeId = sizeId;
		this.colorId = colorId;
		this.quantityInStock = quantityInStock;
		this.additionalPrice = additionalPrice;
		this.weight = weight;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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