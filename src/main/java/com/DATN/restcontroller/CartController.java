package com.DATN.restcontroller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DATN.DTO.CartDTO;
import com.DATN.mapper.CartMapper;
import com.DATN.model.Cart;
import com.DATN.model.CartItem;
import com.DATN.model.ProductVariant;
import com.DATN.model.Users;
import com.DATN.repository.CartItemRepository;
import com.DATN.repository.CartRepository;
import com.DATN.repository.ProductVariantRepository;
import com.DATN.repository.UserRepository;
import com.DATN.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"}) // FE domain
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVariantRepository variantRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartMapper cartMapper;

    /**
     * Lấy giỏ hàng theo userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable Integer userId) {
        try {
            CartDTO dto = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
    /**
     * Add to cart
     */
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> payload) {
        try {
            // --- Parse userId ---
            Integer userId = null;
            Object u = payload.get("userId");
            if (u instanceof Number) {
                userId = ((Number) u).intValue();
            }

            // --- Parse variantId ---
            Integer variantId = null;
            Object v = payload.get("variantId");
            if (v instanceof Number) {
                variantId = ((Number) v).intValue();
            } else if (v instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) v;
                Object id = map.get("variantId");
                if (id instanceof Number) variantId = ((Number) id).intValue();
            }

            if (variantId == null) {
                return ResponseEntity.badRequest().body("Thiếu variantId");
            }

            // --- Parse quantity ---
            Integer quantity = 1;
            Object q = payload.get("quantity");
            if (q instanceof Number) {
                quantity = ((Number) q).intValue();
            }

            // --- Session ID (for guest) ---
            String sessionId = (String) payload.get("sessionId");

            // --- Lấy product variant ---
            ProductVariant variant = variantRepository.findById(variantId).orElse(null);
            if (variant == null) return ResponseEntity.badRequest().body("Biến thể sản phẩm không tồn tại");

            Cart cart = null;

            if (userId != null) {
                // Logged-in user
                Users user = userRepository.findById(userId).orElse(null);
                if (user == null) return ResponseEntity.badRequest().body("User không tồn tại");

                cart = cartRepository.findByUser_UserID(userId).orElse(null);
                if (cart == null) {
                    cart = new Cart();
                    cart.setUser(user);
                    cart.setCreatedAt(LocalDateTime.now());
                    cart.setUpdatedAt(LocalDateTime.now());
                    cart = cartRepository.save(cart);
                }
            } else {
                // Guest user
                if (sessionId == null || sessionId.isEmpty()) return ResponseEntity.badRequest().body("Guest cần sessionId");

                cart = cartRepository.findBySessionId(sessionId).orElse(null);
                if (cart == null) {
                    cart = new Cart();
                    cart.setSessionId(sessionId);
                    cart.setCreatedAt(LocalDateTime.now());
                    cart.setUpdatedAt(LocalDateTime.now());
                    cart = cartRepository.save(cart);
                }
            }

            // --- Thêm / cập nhật CartItem ---
            CartItem item = null;
            for (CartItem i : cart.getItems()) {
                if (i.getVariant().getVariantId().equals(variantId)) {
                    item = i;
                    break;
                }
            }

            if (item == null) {
                item = new CartItem();
                item.setCart(cart);
                item.setVariant(variant);
                item.setQuantity(quantity);
                item.setUnitPrice(variant.getPrice());
                cart.getItems().add(item);
            } else {
                item.setQuantity(item.getQuantity() + quantity);
            }

            cart.setUpdatedAt(LocalDateTime.now());
            cartRepository.save(cart);

            CartDTO dto = cartMapper.toCartDTO(cart);
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi khi thêm vào giỏ hàng: " + e.getMessage());
        }
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody Map<String, Object> payload) {
        try {
            Integer cartItemId = (Integer) payload.get("cartItemId");
            Integer quantity = (Integer) payload.get("quantity");

            CartItem item = cartItemRepository.findById(cartItemId)
                    .orElse(null);
            if (item == null) {
                return ResponseEntity.badRequest().body("Cart item không tồn tại");
            }

            item.setQuantity(quantity);
            cartItemRepository.save(item);

            Cart cart = item.getCart();
            return ResponseEntity.ok(cartMapper.toCartDTO(cart));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi cập nhật số lượng");
        }
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId).orElse(null);
        if (item == null) {
            return ResponseEntity.badRequest().body("Cart item không tồn tại");
        }

        Cart cart = item.getCart(); 
        cartItemRepository.delete(item);

        return ResponseEntity.ok(cartMapper.toCartDTO(cart));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Integer userId) {
        Cart cart = cartRepository.findByUser_UserID(userId).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().body("Giỏ hàng không tồn tại");
        }

        cart.getItems().clear();
        cartRepository.save(cart);

        return ResponseEntity.ok(cartMapper.toCartDTO(cart));
    }

    
}
