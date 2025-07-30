package com.ecommerce.genz_fashion.dto;


import com.ecommerce.genz_fashion.entity.Products;

public class productDTO {
    private Long productId;
    private String sku;
    private String barcode;
    private String name;
    private String description;
    private Double basePrice;
    private String categoryName;
    private String brandName;

    // Constructor từ Entity
    public productDTO(Products product) {
        this.productId = product.getProductId();
        this.sku = product.getSku();
        this.barcode = product.getBarcode();
        this.name = product.getName();
        this.description = product.getDescription();
        this.basePrice = product.getBasePrice();
        this.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        this.brandName = product.getBrand() != null ? product.getBrand().getBrandName() : null;
    }

    public productDTO() {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
// Getters/setters (hoặc dùng Lombok @Data nếu muốn)
}
