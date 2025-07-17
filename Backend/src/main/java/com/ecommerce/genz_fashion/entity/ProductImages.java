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
@Table(name="ProductImages")
public class ProductImages {
	@Id
    private Long imageId;
    private Long productId;
    private Long variantId;
    private String imageUrl;
    private String altText;
    private String imageType;
    private Integer displayOrder;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
	public ProductImages() {
		super();
	}

	public ProductImages(Long imageId, Long productId, Long variantId, String imageUrl, String altText,
			String imageType, Integer displayOrder, Boolean isActive, Date createdAt) {
		super();
		this.imageId = imageId;
		this.productId = productId;
		this.variantId = variantId;
		this.imageUrl = imageUrl;
		this.altText = altText;
		this.imageType = imageType;
		this.displayOrder = displayOrder;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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
    
    
}