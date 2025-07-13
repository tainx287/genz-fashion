package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Sizes")
public class Sizes {
	@Id
    private Long sizeId;
    private String sizeName;
    private Integer sizeOrder;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
	public Sizes() {
		super();
	}

	public Sizes(Long sizeId, String sizeName, Integer sizeOrder, Boolean isActive, Date createdAt) {
		super();
		this.sizeId = sizeId;
		this.sizeName = sizeName;
		this.sizeOrder = sizeOrder;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public Integer getSizeOrder() {
		return sizeOrder;
	}

	public void setSizeOrder(Integer sizeOrder) {
		this.sizeOrder = sizeOrder;
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