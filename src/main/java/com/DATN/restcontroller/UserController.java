package com.DATN.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.model.Users;
import com.DATN.repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:4200"
}, allowCredentials = "true")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Lấy user đang login dựa trên token (mock tạm)
    @GetMapping("/me")
    public Users getCurrentUser(@RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Chưa có token");
        }

        // Mock: giả sử token là userId dưới dạng string
        // Ví dụ token = "1"
        Integer userId;
        try {
            userId = Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Token không hợp lệ");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }
}

