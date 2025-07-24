package com.ecommerce.genz_fashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.genz_fashion.entity.Sizes;
import com.ecommerce.genz_fashion.repository.SizesRepository;

@Service
public class SizesService {
    
    @Autowired
    private SizesRepository sizesRepository;
    
    public List<Sizes> getAllSizes() {
        return sizesRepository.findAll();
    }
    
    public Optional<Sizes> getSizeById(Long id) {
        return sizesRepository.findById(id);
    }
    
    public Sizes saveSize(Sizes size) {
        return sizesRepository.save(size);
    }
    
    public void deleteSize(Long id) {
        sizesRepository.deleteById(id);
    }
    
    public List<Sizes> getActiveSizes() {
        return sizesRepository.findAll().stream()
                .filter(size -> size.getIsActive() != null && size.getIsActive())
                .sorted((s1, s2) -> Integer.compare(
                    s1.getSizeOrder() != null ? s1.getSizeOrder() : 0,
                    s2.getSizeOrder() != null ? s2.getSizeOrder() : 0))
                .toList();
    }
}