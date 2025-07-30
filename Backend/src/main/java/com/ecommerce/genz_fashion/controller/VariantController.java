package com.ecommerce.genz_fashion.controller;


import com.ecommerce.genz_fashion.dto.ProductVariantDTO;
import com.ecommerce.genz_fashion.entity.ProductVariants;
import com.ecommerce.genz_fashion.service.products.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/variant")
public class VariantController {

    @Autowired
    private VariantService variantService;

    @GetMapping
    public Page<ProductVariantDTO> fillAll(Pageable pageable) {
        return variantService.getAllVariants(pageable);
    }

}
