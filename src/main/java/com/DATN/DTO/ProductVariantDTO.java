package com.DATN.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductVariantDTO {
    private Integer variantId;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stock;
}
