package com.ecommerce.genz_fashion.entity;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Products")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;
	
    @NotBlank(message = "SKU không được để trống")
    @Size(max = 50, message = "SKU không được vượt quá 50 ký tự")
    private String sku;
    
    @Size(max = 100, message = "Barcode không được vượt quá 100 ký tự")
    private String barcode;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm không được vượt quá 100 ký tự")
    private String name;
    
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;
    
    @NotNull(message = "Giá cơ bản không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @Column(name = "base_price")
    private Double basePrice;
    
    // JPA Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brands brand;
    @Column(name = "is_active")
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at")
    private Date deletedAt;
    
	public Products() {
		super();
	}

	public Products(Long productId, String sku, String barcode, String name, String description, Double basePrice,
			Categories category, Brands brand, Boolean isActive, Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.productId = productId;
		this.sku = sku;
		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.basePrice = basePrice;
		this.category = category;
		this.brand = brand;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public Brands getBrand() {
		return brand;
	}

	public void setBrand(Brands brand) {
		this.brand = brand;
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

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
    
	
}