package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="UserPoints")
public class UserPoints {
	@Id
    private Long pointId;
    private Long userId;
    private Integer pointsBalance;
    @Temporal(TemporalType.DATE)
    private Date lastUpdated;
    
	public UserPoints() {
		super();
	}

	public UserPoints(Long pointId, Long userId, Integer pointsBalance, Date lastUpdated) {
		super();
		this.pointId = pointId;
		this.userId = userId;
		this.pointsBalance = pointsBalance;
		this.lastUpdated = lastUpdated;
	}

	public Long getPointId() {
		return pointId;
	}

	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getPointsBalance() {
		return pointsBalance;
	}

	public void setPointsBalance(Integer pointsBalance) {
		this.pointsBalance = pointsBalance;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
    
    
}