package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Categories")
public class Categories {
	@Id
    private Long categoryId;
    private String name;
    private String description;
    private Long parentId;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date deletedAt;
    
    
	public Categories() {
		super();
	}


	public Categories(Long categoryId, String name, String description, Long parentId, Boolean isActive, Date createdAt,
			Date deletedAt) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.parentId = parentId;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
	}


	public Long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
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


	public Date getDeletedAt() {
		return deletedAt;
	}


	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
    
	
    
}