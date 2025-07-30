package com.ecommerce.genz_fashion.service.products;


import com.ecommerce.genz_fashion.dto.ProductVariantDTO;
import com.ecommerce.genz_fashion.entity.ProductVariants;
import com.ecommerce.genz_fashion.repository.product.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {

    @Autowired
    private VariantRepository variantRepository;

    public Page<ProductVariantDTO> getAllVariants(Pageable pageable) {
        Page<ProductVariants> variants = variantRepository.findALL(pageable);
        return variants.map(ProductVariantDTO::new);
    }

}
