package com.DATN.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DATN.DTO.CartDTO;
import com.DATN.mapper.CartMapper;
import com.DATN.model.Cart;
import com.DATN.model.CartItem;
import com.DATN.repository.CartItemRepository;
import com.DATN.repository.CartRepository;



@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartMapper cartMapper;

    public CartDTO getCartByUserId(Integer userId) {
        Cart cart = cartRepository.findByUser_UserID(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        return cartMapper.toCartDTO(cart);
    }

//    public boolean removeItem(Integer cartItemId) {
//        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
//        if (item.isEmpty()) {
//            return false; // Không tồn tại item
//        }
//
//        cartItemRepository.deleteById(cartItemId);
//        return true;
//    }
}