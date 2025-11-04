package com.DATN.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DATN.DTO.CartDTO;
import com.DATN.DTO.CartItemDTO;
import com.DATN.model.Cart;
import com.DATN.model.CartItem;
import com.DATN.model.ProductVariant;
import com.DATN.model.Users;
import com.DATN.repository.CartItemRepository;
import com.DATN.repository.CartRepository;
import com.DATN.repository.ProductVariantRepository;
import com.DATN.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository variantRepository;

    // --- Lấy giỏ hàng theo user ---
    public CartDTO getCartByUser(Integer userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser_UserID(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });


        return mapToDTO(cart);
    }

    // --- Thêm sản phẩm vào giỏ ---
    public CartDTO addToCart(Integer userId, Integer variantId, Integer quantity) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Product variant not found"));

        Cart cart = cartRepository.findAll().stream()
                .filter(c -> c.getUser().getUserID().equals(userId))
                .findFirst()
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // kiểm tra nếu sản phẩm đã có trong giỏ
        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getVariant().getVariantId().equals(variantId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setUnitPrice(variant.getPrice());
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(variant.getPrice());
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return mapToDTO(cart);
    }

    // --- Cập nhật số lượng ---
    public void updateQuantity(Integer itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    // --- Xóa sản phẩm ---
    public void removeItem(Integer itemId) {
        cartItemRepository.deleteById(itemId);
    }

    // --- Map entity sang DTO ---
    private CartDTO mapToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUser().getUserID());
        dto.setItems(cart.getItems().stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private CartItemDTO mapItemToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(item.getCartItemId());
        dto.setVariantId(item.getVariant().getVariantId());
        dto.setProductName(item.getVariant().getProduct().getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getUnitPrice());
        return dto;
    }
}