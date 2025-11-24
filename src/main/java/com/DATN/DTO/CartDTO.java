package com.DATN.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    private Integer cartId;
    private Integer userId;
    private List<CartItemDTO> items;
    private BigDecimal totalAmount; // tổng tiền
}