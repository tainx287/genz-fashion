package com.ecommerce.genz_fashion.controller.admin;

import com.ecommerce.genz_fashion.service.ProductService;
import com.ecommerce.genz_fashion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final ProductService productService;
    private final UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Nên có các phương thức count() riêng trong service để hiệu quả hơn
        model.addAttribute("totalProducts", productService.countAllProducts());
        model.addAttribute("totalUsers", userService.countAllUsers());
        return "admin/dashboard";
    }
    
    @GetMapping("/products")
    public String manageProducts(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Products> productPage = productService.getProductsWithPagination(pageable); // Cần thêm phương thức này vào service
        model.addAttribute("productPage", productPage);
        return "admin/products";
    }
    
    @GetMapping("/users")
    public String manageUsers(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.getUsersWithPagination(pageable); // Cần thêm phương thức này vào service
        model.addAttribute("userPage", userPage);
        return "admin/users";
    }
}