package com.ecommerce.genz_fashion.controller.web;

import com.ecommerce.genz_fashion.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featuredProducts", productService.getFeaturedProducts());
        return "index";
    }
    
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getActiveProducts());
        return "products";
    }
}