package com.ecommerce.genz_fashion.controller.api;

import com.ecommerce.genz_fashion.dto.AuthDto;
import com.ecommerce.genz_fashion.service.AuthService;
import com.ecommerce.genz_fashion.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto.LoginRequest loginRequest) {
        // AuthenticationManager sẽ tự ném BadCredentialsException nếu sai,
        // GlobalExceptionHandler sẽ bắt và xử lý.
        AuthDto.LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDto.RegisterRequest registerRequest) {
        // AuthService sẽ ném DuplicateResourceException nếu username/email đã tồn tại.
        // GlobalExceptionHandler sẽ bắt và trả về lỗi 409 Conflict.
        AuthDto.RegisterResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody AuthDto.ChangePasswordRequest request) {
        // Service sẽ ném exception nếu mật khẩu cũ sai hoặc user không tồn tại.
        authService.changePassword(request.getUsername(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody AuthDto.ResetPasswordRequest request) {
        // Service sẽ ném ResourceNotFoundException nếu không tìm thấy email.
        authService.resetPassword(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
    }
    
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        if (isValid) {
            return ResponseEntity.ok(new MessageResponse("Token is valid"));
        }
        // Nếu token không hợp lệ, service sẽ trả về false, client sẽ nhận lỗi 400.
        return ResponseEntity.badRequest().body(new MessageResponse("Token is invalid or expired"));
    }
}