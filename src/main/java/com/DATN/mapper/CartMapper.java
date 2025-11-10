package com.DATN.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.DATN.DTO.CartDTO;
import com.DATN.DTO.CartItemDTO;
import com.DATN.model.Cart;
import com.DATN.model.CartItem;

@Component
public class CartMapper {

    public CartDTO toCartDTO(Cart cart) {
        if (cart == null) return null;

        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUser().getUserID());

        List<CartItemDTO> items = cart.getItems().stream()
            .map(this::toCartItemDTO)
            .collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }

    private CartItemDTO toCartItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(item.getCartItemId());
        dto.setVariantId(item.getVariant().getVariantId());
        dto.setProductName(item.getVariant().getProduct().getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getUnitPrice());

        // thêm thông tin variant
        dto.setSize(item.getVariant().getSize());
        dto.setColor(item.getVariant().getColor());
        dto.setColorCode(item.getVariant().getColor_code());

        // lấy ảnh đầu tiên nếu có
        dto.setImageUrl(item.getVariant().getProduct().getImages().isEmpty() 
                ? null 
                : item.getVariant().getProduct().getImages().get(0).getImageUrl());

        return dto;
    }

}
