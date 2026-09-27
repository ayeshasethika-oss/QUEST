package com.quest.app.controller;

import com.quest.app.model.User;
import com.quest.app.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> body, HttpSession session) {
        try {
            String fullName = (String) body.get("fullName");
            Integer age = Integer.parseInt(body.get("age").toString());
            String gender = (String) body.get("gender");
            String phone = (String) body.get("phone");
            String email = (String) body.get("email");
            String password = (String) body.get("password");
            String profilePhoto = (String) body.get("profilePhoto");

            User user = authService.registerUser(fullName, age, gender, phone, email, password, profilePhoto);
            session.setAttribute("loggedInUser", user);

            return ResponseEntity.ok(Map.of("success", true, "message", "Registration successful!", "data", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body, HttpSession session) {
        String otp = body.get("otp");
        if ("123456".equals(otp) || (otp != null && otp.length() == 6)) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Demo OTP Verified Successfully!"));
        }
        return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid OTP code. Use demo code: 123456"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpSession session) {
        try {
            String email = body.get("email");
            String password = body.get("password");

            User user = authService.loginUser(email, password);
            session.setAttribute("loggedInUser", user);

            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful!", "data", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("success", true, "message", "Logged out successfully!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return ResponseEntity.ok(Map.of("success", true, "message", "Password reset instructions sent to " + email + ". Demo OTP code: 123456"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String newPassword = body.get("newPassword");
            authService.resetPassword(email, newPassword);
            return ResponseEntity.ok(Map.of("success", true, "message", "Password reset successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
