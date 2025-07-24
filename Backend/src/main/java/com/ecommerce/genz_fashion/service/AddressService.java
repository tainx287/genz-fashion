package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.genz_fashion.entity.Address;
import com.ecommerce.genz_fashion.repository.AddressRepository;

@Service
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
    
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }
    
    public Address saveAddress(Address address) {
        if (address.getCreatedAt() == null) {
            address.setCreatedAt(new Date());
        }
        return addressRepository.save(address);
    }
    
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
    
    public List<Address> getActiveAddresses() {
        return addressRepository.findAll().stream()
                .filter(address -> address.getIsActive() != null && address.getIsActive())
                .toList();
    }
    
    public Address createAddress(String streetAddress, String ward, String district, 
                               String city, String province, String postalCode, String country) {
        Address address = new Address();
        address.setStreetAddress(streetAddress);
        address.setWard(ward);
        address.setDistrict(district);
        address.setCity(city);
        address.setProvince(province);
        address.setPostalCode(postalCode);
        address.setCountry(country != null ? country : "Vietnam");
        address.setIsActive(true);
        address.setCreatedAt(new Date());
        return saveAddress(address);
    }
}