package com.ecommerce.genzfashion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults()) // Kích hoạt CORS với cấu hình bên dưới
            .csrf(csrf -> csrf.disable()) // Tạm thời vô hiệu hóa CSRF để dễ test API (nên bật trong môi trường production)
            .authorizeHttpRequests(authz -> authz
                // Cho phép tất cả các request trong giai đoạn phát triển để dễ dàng kiểm thử
                // CẢNH BÁO: Không sử dụng cấu hình này trong môi trường production vì nó vô hiệu hóa toàn bộ bảo mật.
                .anyRequest().permitAll()
                // --- HOẶC, cách an toàn hơn là chỉ cho phép các đường dẫn cụ thể ---
                // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/**", "/products/**", "/users/**").permitAll()
                // .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Thay thế bằng địa chỉ của frontend, ví dụ: React, Angular, Vue...
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200", "http://127.0.0.1:5500"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cấu hình CORS cho tất cả các đường dẫn
        return source;
    }
}