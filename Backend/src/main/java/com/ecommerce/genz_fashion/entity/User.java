package com.ecommerce.genz_fashion.entity;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
    private Long userId;
    
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự")
    private String username;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    @Column(name = "password_hash")
    private String passwordHash;
    
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    @Column(name = "full_name")
    private String fullName;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Số điện thoại không hợp lệ")
    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    @Column(name = "phone_number")
    private String phoneNumber;
    
    // JPA Relationship with Address
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updatedAt;
    @Temporal(TemporalType.DATE)
    @Column(name = "deleted_at")
    private Date deletedAt;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "is_active")
    private Boolean isActive;
    
    public enum Role {
        customer, admin, staff
    }
    
	public User() {
		super();
	}

	public User(Long userId, String username, String email, String passwordHash, String fullName, String phoneNumber,
			Long addressId, Date createdAt, Date updatedAt, Date deletedAt, Role role, Boolean isActive) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.addressId = addressId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.role = role;
		this.isActive = isActive;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	// Additional methods for AuthService compatibility
	public String getPassword() {
		return passwordHash;
	}
	
	public void setPassword(String password) {
		this.passwordHash = password;
	}
	
	public String getPhone() {
		return phoneNumber;
	}
	
	public void setPhone(String phone) {
		this.phoneNumber = phone;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		// Convert LocalDateTime to Date for compatibility
		this.createdAt = java.sql.Timestamp.valueOf(createdAt);
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		// Convert LocalDateTime to Date for compatibility
		this.updatedAt = java.sql.Timestamp.valueOf(updatedAt);
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
    
    
}