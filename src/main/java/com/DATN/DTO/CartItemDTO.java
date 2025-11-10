package com.DATN.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDTO {
    private Integer cartItemId;
    private Integer variantId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;

    private String imageUrl;
    private String size;
    private String color;
    private String colorCode;
    
    
}