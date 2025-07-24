package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.genz_fashion.entity.Brands;
import com.ecommerce.genz_fashion.repository.BrandsRepository;

@Service
public class BrandsService {
    
    @Autowired
    private BrandsRepository brandsRepository;
    
    public List<Brands> getAllBrands() {
        return brandsRepository.findAll();
    }
    
    public Optional<Brands> getBrandById(Long id) {
        return brandsRepository.findById(id);
    }
    
    public Brands saveBrand(Brands brand) {
        if (brand.getCreatedAt() == null) {
            brand.setCreatedAt(new Date());
        }
        brand.setUpdatedAt(new Date());
        return brandsRepository.save(brand);
    }
    
    public void deleteBrand(Long id) {
        brandsRepository.deleteById(id);
    }
    
    public List<Brands> getActiveBrands() {
        return brandsRepository.findAll().stream()
                .filter(brand -> brand.getIsActive() != null && brand.getIsActive())
                .toList();
    }
    
    public Brands createBrand(String brandName, String description, String logoUrl, 
                             String website, String contactEmail, String contactPhone) {
        Brands brand = new Brands();
        brand.setBrandName(brandName);
        brand.setDescription(description);
        brand.setLogoUrl(logoUrl);
        brand.setWebsite(website);
        brand.setContactEmail(contactEmail);
        brand.setContactPhone(contactPhone);
        brand.setIsActive(true);
        return saveBrand(brand);
    }
    
    public Brands updateBrand(Long id, Brands updatedBrand) {
        Optional<Brands> existingBrand = getBrandById(id);
        if (existingBrand.isPresent()) {
            Brands brand = existingBrand.get();
            brand.setBrandName(updatedBrand.getBrandName());
            brand.setDescription(updatedBrand.getDescription());
            brand.setLogoUrl(updatedBrand.getLogoUrl());
            brand.setWebsite(updatedBrand.getWebsite());
            brand.setContactEmail(updatedBrand.getContactEmail());
            brand.setContactPhone(updatedBrand.getContactPhone());
            brand.setIsActive(updatedBrand.getIsActive());
            return saveBrand(brand);
        }
        return null;
    }
}