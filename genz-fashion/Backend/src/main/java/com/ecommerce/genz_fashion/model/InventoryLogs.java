package com.genzfashion.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="InventoryLogs")
public class InventoryLogs {
	@Id
    private Long logId;
    private Long variantId;
    private String changeType;
    private Integer quantityBefore;
    private Integer quantityChanged;
    private Integer quantityAfter;
    private String reason;
    private String referenceType;
    private Long referenceId;
    private Long changedBy;
    @Temporal(TemporalType.DATE)
    private Date changedAt;
    
    
	public InventoryLogs() {
		super();
	}


	public InventoryLogs(Long logId, Long variantId, String changeType, Integer quantityBefore, Integer quantityChanged,
			Integer quantityAfter, String reason, String referenceType, Long referenceId, Long changedBy,
			Date changedAt) {
		super();
		this.logId = logId;
		this.variantId = variantId;
		this.changeType = changeType;
		this.quantityBefore = quantityBefore;
		this.quantityChanged = quantityChanged;
		this.quantityAfter = quantityAfter;
		this.reason = reason;
		this.referenceType = referenceType;
		this.referenceId = referenceId;
		this.changedBy = changedBy;
		this.changedAt = changedAt;
	}


	public Long getLogId() {
		return logId;
	}


	public void setLogId(Long logId) {
		this.logId = logId;
	}


	public Long getVariantId() {
		return variantId;
	}


	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}


	public String getChangeType() {
		return changeType;
	}


	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}


	public Integer getQuantityBefore() {
		return quantityBefore;
	}


	public void setQuantityBefore(Integer quantityBefore) {
		this.quantityBefore = quantityBefore;
	}


	public Integer getQuantityChanged() {
		return quantityChanged;
	}


	public void setQuantityChanged(Integer quantityChanged) {
		this.quantityChanged = quantityChanged;
	}


	public Integer getQuantityAfter() {
		return quantityAfter;
	}


	public void setQuantityAfter(Integer quantityAfter) {
		this.quantityAfter = quantityAfter;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getReferenceType() {
		return referenceType;
	}


	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}


	public Long getReferenceId() {
		return referenceId;
	}


	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
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
    
	
    
}