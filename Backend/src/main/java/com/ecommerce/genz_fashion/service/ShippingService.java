package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.genz_fashion.entity.Shipping;
import com.ecommerce.genz_fashion.repository.ShippingRepository;

@Service
@Transactional
public class ShippingService {
    
    @Autowired
    private ShippingRepository shippingRepository;
    
    public List<Shipping> getAllShippings() {
        return shippingRepository.findAll();
    }
    
    public Optional<Shipping> getShippingById(Long id) {
        return shippingRepository.findById(id);
    }
    
    public Optional<Shipping> getShippingByOrderId(Long orderId) {
        return shippingRepository.findByOrderId(orderId);
    }
    
    public Shipping saveShipping(Shipping shipping) {
        if (shipping.getCreatedAt() == null) {
            shipping.setCreatedAt(new Date());
        }
        return shippingRepository.save(shipping);
    }
    
    public void deleteShipping(Long id) {
        shippingRepository.deleteById(id);
    }
    
    public Shipping createShipping(Long orderId, String shippingMethod, Double shippingFee) {
        Shipping shipping = new Shipping();
        shipping.setOrderId(orderId);
        shipping.setShippingMethod(shippingMethod);
        shipping.setShippingFee(shippingFee);
        shipping.setShippingStatus("processing");
        shipping.setCreatedAt(new Date());
        return saveShipping(shipping);
    }
    
    public Shipping updateShippingStatus(Long shippingId, String status) {
        Optional<Shipping> shipping = getShippingById(shippingId);
        if (shipping.isPresent()) {
            shipping.get().setShippingStatus(status);
            if ("shipped".equals(status)) {
                shipping.get().setShippedAt(new Date());
            }
            return saveShipping(shipping.get());
        }
        throw new RuntimeException("Shipping not found");
    }
    
    public Shipping updateTrackingNumber(Long shippingId, String trackingNumber) {
        Optional<Shipping> shipping = getShippingById(shippingId);
        if (shipping.isPresent()) {
            shipping.get().setTrackingNumber(trackingNumber);
            return saveShipping(shipping.get());
        }
        throw new RuntimeException("Shipping not found");
    }
    
    public List<Shipping> getShippingsByStatus(String status) {
        return shippingRepository.findByShippingStatus(status);
    }
}