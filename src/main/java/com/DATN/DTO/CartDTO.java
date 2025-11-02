package com.DATN.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    private Integer cartId;
    private Integer userId;
    private List<CartItemDTO> items;
}
