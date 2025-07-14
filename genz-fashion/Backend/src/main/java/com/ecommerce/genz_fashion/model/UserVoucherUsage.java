package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="UserVoucherUsage")
public class UserVoucherUsage {
	@Id
    private Long usageId;
    private Long userId;
    private Long voucherId;
    private Integer pointsSpent;
    @Temporal(TemporalType.DATE)
    private Date usedAt;
    private Long orderId;
    
	public UserVoucherUsage() {
		super();
	}

	public UserVoucherUsage(Long usageId, Long userId, Long voucherId, Integer pointsSpent, Date usedAt, Long orderId) {
		super();
		this.usageId = usageId;
		this.userId = userId;
		this.voucherId = voucherId;
		this.pointsSpent = pointsSpent;
		this.usedAt = usedAt;
		this.orderId = orderId;
	}

	public Long getUsageId() {
		return usageId;
	}

	public void setUsageId(Long usageId) {
		this.usageId = usageId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Integer getPointsSpent() {
		return pointsSpent;
	}

	public void setPointsSpent(Integer pointsSpent) {
		this.pointsSpent = pointsSpent;
	}

	public Date getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(Date usedAt) {
		this.usedAt = usedAt;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
    
    
}