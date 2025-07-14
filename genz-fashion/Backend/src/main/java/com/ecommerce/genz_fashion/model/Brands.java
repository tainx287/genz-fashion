package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Brands")
public class Brands {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String brandName;
    private String description;
    private String logoUrl;
    private String website;
    private String contactEmail;
    private String contactPhone;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    
    
	public Brands() {
		super();
	}


	public Brands(Long brandId, String brandName, String description, String logoUrl, String website,
			String contactEmail, String contactPhone, Boolean isActive, Date createdAt, Date updatedAt) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.description = description;
		this.logoUrl = logoUrl;
		this.website = website;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	public Long getBrandId() {
		return brandId;
	}


	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLogoUrl() {
		return logoUrl;
	}


	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}


	public String getWebsite() {
		return website;
	}


	public void setWebsite(String website) {
		this.website = website;
	}


	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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