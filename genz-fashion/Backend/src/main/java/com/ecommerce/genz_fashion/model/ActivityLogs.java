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
@Table(name = "ActivityLogs")
public class ActivityLogs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
	
    private String tableName;
    private String actionType;
    private Long recordId;
    private String oldValues;
    private String newValues;
    private Long changedBy;
    @Temporal(TemporalType.DATE)
    private Date changedAt;
    private String ipAddress;
    private String userAgent;
    
    
	public ActivityLogs() {
		super();
	}


	public ActivityLogs(Long logId, String tableName, String actionType, Long recordId, String oldValues,
			String newValues, Long changedBy, Date changedAt, String ipAddress, String userAgent) {
		super();
		this.logId = logId;
		this.tableName = tableName;
		this.actionType = actionType;
		this.recordId = recordId;
		this.oldValues = oldValues;
		this.newValues = newValues;
		this.changedBy = changedBy;
		this.changedAt = changedAt;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
	}


	public Long getLogId() {
		return logId;
	}


	public void setLogId(Long logId) {
		this.logId = logId;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getActionType() {
		return actionType;
	}


	public void setActionType(String actionType) {
		this.actionType = actionType;
	}


	public Long getRecordId() {
		return recordId;
	}


	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}


	public String getOldValues() {
		return oldValues;
	}


	public void setOldValues(String oldValues) {
		this.oldValues = oldValues;
	}


	public String getNewValues() {
		return newValues;
	}


	public void setNewValues(String newValues) {
		this.newValues = newValues;
	}


	public Long getChangedBy() {
		return changedBy;
	}


	public void setChangedBy(Long changedBy) {
		this.changedBy = changedBy;
	}


	public Date getChangedAt() {
		return changedAt;
	}


	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getUserAgent() {
		return userAgent;
	}


	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	
    
    
}