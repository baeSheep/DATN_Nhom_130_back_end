package com.DATN.restcontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DATN.model.Users;
import com.DATN.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:4200"
}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // --- Đăng ký ---
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        boolean emailExists = userRepository.findAll()
                .stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));

        if (emailExists) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }

        // Không mã hoá mật khẩu ở giai đoạn này (theo yêu cầu)
        user.setRoleId(1); // Gán mặc định role user
        Users savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    // --- Đăng nhập ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users loginReq) {
        Users user = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(loginReq.getEmail()))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("Email không tồn tại");
        }

        // Không dùng BCrypt — so sánh trực tiếp
        if (!loginReq.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Sai mật khẩu");
        }

        // Tạo token giả lập (UUID ngẫu nhiên)
        String fakeToken = UUID.randomUUID().toString();

        // Trả thông tin cần thiết
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserID());
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("roleId", user.getRoleId());
        response.put("token", fakeToken);

        return ResponseEntity.ok(response);
    }
}
