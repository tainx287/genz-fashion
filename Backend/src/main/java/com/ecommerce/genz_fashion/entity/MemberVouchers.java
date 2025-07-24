package com.ecommerce.genz_fashion.entity;

import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name="MemberVouchers")
public class MemberVouchers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_voucher_id")
    private Long memberVoucherId;
    
    private String code;
    private String name;
    private String description;
    
    @Column(name = "discount_type")
    private String discountType;
    
    @Column(name = "discount_value")
    private Double discountValue;
    
    @Column(name = "min_purchase_amount")
    private Double minPurchaseAmount;
    
    @Column(name = "max_discount_amount")
    private Double maxDiscountAmount;
    
    @Column(name = "required_points")
    private Integer requiredPoints;
    
    @Column(name = "usage_limit_per_user")
    private Integer usageLimitPerUser;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    public MemberVouchers() {
        super();
    }

    public MemberVouchers(Long memberVoucherId, String code, String name, String description, String discountType,
            Double discountValue, Double minPurchaseAmount, Double maxDiscountAmount, Integer requiredPoints,
            Integer usageLimitPerUser, Date startDate, Date endDate, Boolean isActive, Date createdAt) {
        super();
        this.memberVoucherId = memberVoucherId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPurchaseAmount = minPurchaseAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.requiredPoints = requiredPoints;
        this.usageLimitPerUser = usageLimitPerUser;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getMemberVoucherId() {
        return memberVoucherId;
    }

    public void setMemberVoucherId(Long memberVoucherId) {
        this.memberVoucherId = memberVoucherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(Double minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(Double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public Integer getRequiredPoints() {
        return requiredPoints;
    }

    public void setRequiredPoints(Integer requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    public Integer getUsageLimitPerUser() {
        return usageLimitPerUser;
    }

    public void setUsageLimitPerUser(Integer usageLimitPerUser) {
        this.usageLimitPerUser = usageLimitPerUser;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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