package com.ecommerce.genz_fashion.entity;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="UserOAuth")
public class UserOAuth {
	@Id
    private Long oauthId;
    private Long userId;
    private String provider;
    private String providerUserId;
    private String providerToken;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    
	public UserOAuth() {
		super();
	}

	public UserOAuth(Long oauthId, Long userId, String provider, String providerUserId, String providerToken,
			Date createdAt, Date updatedAt) {
		super();
		this.oauthId = oauthId;
		this.userId = userId;
		this.provider = provider;
		this.providerUserId = providerUserId;
		this.providerToken = providerToken;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getOauthId() {
		return oauthId;
	}

	public void setOauthId(Long oauthId) {
		this.oauthId = oauthId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getProviderToken() {
		return providerToken;
	}

	public void setProviderToken(String providerToken) {
		this.providerToken = providerToken;
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
    
    
}