package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PublicVouchers")
public class PublicVouchers {
	@Id
    private Long voucherId;
    private String code;
    private String name;
    private String description;
    private String discountType;
    private Double discountValue;
    private Double minPurchaseAmount;
    private Double maxDiscountAmount;
    private Integer requiredPoints;
    private Integer usageLimit;
    private Integer usageLimitPerUser;
    private Integer usedCount;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private Boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    
	public PublicVouchers() {
		super();
	}

	public PublicVouchers(Long voucherId, String code, String name, String description, String discountType,
			Double discountValue, Double minPurchaseAmount, Double maxDiscountAmount, Integer requiredPoints,
			Integer usageLimit, Integer usageLimitPerUser, Integer usedCount, Date startDate, Date endDate,
			Boolean isActive, Date createdAt) {
		super();
		this.voucherId = voucherId;
		this.code = code;
		this.name = name;
		this.description = description;
		this.discountType = discountType;
		this.discountValue = discountValue;
		this.minPurchaseAmount = minPurchaseAmount;
		this.maxDiscountAmount = maxDiscountAmount;
		this.requiredPoints = requiredPoints;
		this.usageLimit = usageLimit;
		this.usageLimitPerUser = usageLimitPerUser;
		this.usedCount = usedCount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
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

	public Integer getUsageLimit() {
		return usageLimit;
	}

	public void setUsageLimit(Integer usageLimit) {
		this.usageLimit = usageLimit;
	}

	public Integer getUsageLimitPerUser() {
		return usageLimitPerUser;
	}

	public void setUsageLimitPerUser(Integer usageLimitPerUser) {
		this.usageLimitPerUser = usageLimitPerUser;
	}

	public Integer getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
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