package com.DATN.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDetailDTO {
	private Integer orderDetailId;
    private Integer variantId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
