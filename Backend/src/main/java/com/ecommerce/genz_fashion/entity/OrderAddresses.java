package com.ecommerce.genz_fashion.entity;

import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name="OrderAddresses")
public class OrderAddresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_address_id")
    private Long orderAddressId;
    
    @Column(name = "street_address")
    private String streetAddress;
    
    private String ward;
    private String district;
    private String city;
    private String province;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    private String country;
    
    @Column(name = "recipient_name")
    private String recipientName;
    
    @Column(name = "recipient_phone")
    private String recipientPhone;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    public OrderAddresses() {
        super();
    }

    public OrderAddresses(Long orderAddressId, String streetAddress, String ward, String district, String city,
            String province, String postalCode, String country, String recipientName, String recipientPhone,
            Date createdAt) {
        super();
        this.orderAddressId = orderAddressId;
        this.streetAddress = streetAddress;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getOrderAddressId() {
        return orderAddressId;
    }

    public void setOrderAddressId(Long orderAddressId) {
        this.orderAddressId = orderAddressId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}