package com.ecommerce.genz_fashion.dto;

import com.ecommerce.genz_fashion.entity.ProductVariants;

public class ProductVariantDTO {
    private Long variantId;
    private String productName;
    private String categoryName;
    private String variantSku;
    private String variantBarcode;
    private String sizeName;
    private Integer quantity;
    private String colorName;
    private Double additionalPrice;
    private Double weight;
    private Boolean isActive;

    public ProductVariantDTO() {
    }

    public ProductVariantDTO(ProductVariants variant) {
        this.variantId = variant.getVariantId();
        this.productName = variant.getProduct_id().getName();
        this.categoryName = variant.getProduct_id().getCategory() != null
                ? variant.getProduct_id().getCategory().getName()
                : null;
        this.variantSku = variant.getVariantSku();
        this.variantBarcode = variant.getVariantBarcode();
        this.sizeName = variant.getSize_id().getSizeName(); // assuming `Sizes` has getName()
        this.colorName = variant.getColor_id().getColorName(); // assuming `Colors` has getName()
        this.quantity = variant.getQuantityInStock();
        this.additionalPrice = variant.getAdditionalPrice();
        this.weight = variant.getWeight();
        this.isActive = variant.getActive();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
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
// Getters (or use @Getter if you're using Lombok)
}
