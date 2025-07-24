package com.ecommerce.genz_fashion.entity;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Categories")
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
    private Long categoryId;
    
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 50, message = "Tên danh mục không được vượt quá 50 ký tự")
    private String name;
    
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;
    
    // Self-referencing relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Categories parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Categories> children = new ArrayList<>();
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Products> products = new ArrayList<>();
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date deletedAt;
    
    
	public Categories() {
		super();
	}


	public Categories(Long categoryId, String name, String description, Categories parent, Boolean isActive, Date createdAt,
			Date deletedAt) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.parent = parent;
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


	public Categories getParent() {
		return parent;
	}

	public void setParent(Categories parent) {
		this.parent = parent;
	}

	public List<Categories> getChildren() {
		return children;
	}

	public void setChildren(List<Categories> children) {
		this.children = children;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
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