package com.ecommerce.genz_fashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.genz_fashion.entity.Colors;
import com.ecommerce.genz_fashion.repository.ColorsRepository;

@Service
public class ColorsService {
    
    @Autowired
    private ColorsRepository colorsRepository;
    
    public List<Colors> getAllColors() {
        return colorsRepository.findAll();
    }
    
    public Optional<Colors> getColorById(Long id) {
        return colorsRepository.findById(id);
    }
    
    public Colors saveColor(Colors color) {
        return colorsRepository.save(color);
    }
    
    public void deleteColor(Long id) {
        colorsRepository.deleteById(id);
    }
    
    public List<Colors> getActiveColors() {
        return colorsRepository.findAll().stream()
                .filter(color -> color.getIsActive() != null && color.getIsActive())
                .toList();
    }
}