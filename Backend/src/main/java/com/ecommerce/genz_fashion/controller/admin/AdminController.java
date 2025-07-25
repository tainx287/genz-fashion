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

    // Lưu ý: Các service này cần được tạo và triển khai
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Gợi ý: Nên có các phương thức count() riêng trong service để hiệu quả hơn
        model.addAttribute("totalProducts", productService.countAllProducts());
        model.addAttribute("totalUsers", userService.countAllUsers());
        return "admin/dashboard"; // Trả về view template
    }

    @GetMapping("/products")
    public String manageProducts(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Lưu ý: Cần tạo model Product và phương thức getProductsWithPagination trong service
        Page<Product> productPage = productService.getProductsWithPagination(pageable);
        model.addAttribute("productPage", productPage);
        return "admin/products";
    }

    // Tương tự, bạn cần tạo các phương thức và model cho Users, Orders, v.v.
}