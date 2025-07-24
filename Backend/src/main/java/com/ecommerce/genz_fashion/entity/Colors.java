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
@Table(name="Colors")
public class Colors {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colorId;
    private String colorName;
    private String colorCode;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
    
	public Colors() {
		super();
	}


	public Colors(Long colorId, String colorName, String colorCode, Boolean isActive, Date createdAt) {
		super();
		this.colorId = colorId;
		this.colorName = colorName;
		this.colorCode = colorCode;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}


	public Long getColorId() {
		return colorId;
	}


	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}


	public String getColorName() {
		return colorName;
	}


	public void setColorName(String colorName) {
		this.colorName = colorName;
	}


	public String getColorCode() {
		return colorCode;
	}


	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
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