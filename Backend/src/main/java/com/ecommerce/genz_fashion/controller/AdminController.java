package com.ecommerce.genzfashion.controllers.admin;

import com.ecommerce.genzfashion.models.Product;
import com.ecommerce.genzfashion.models.User;
import com.ecommerce.genzfashion.service.ProductService;
import com.ecommerce.genzfashion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalProducts", productService.countAllProducts());
        model.addAttribute("totalUsers", userService.countAllUsers());
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String manageProducts(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProductsWithPagination(pageable);
        model.addAttribute("productPage", productPage);
        return "admin/products";
    }
}