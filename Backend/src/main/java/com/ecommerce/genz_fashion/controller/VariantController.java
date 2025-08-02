package com.ecommerce.genz_fashion.controller;


import com.ecommerce.genz_fashion.dto.ProductVariantDTO;
import com.ecommerce.genz_fashion.entity.ProductVariants;
import com.ecommerce.genz_fashion.service.products.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/variant")
@CrossOrigin(origins = "*")
public class VariantController {

    @Autowired
    private VariantService variantService;

    @GetMapping
    public Page<ProductVariantDTO> fillAll(Pageable pageable) {
        return variantService.getAllVariants(pageable);
    }

}
