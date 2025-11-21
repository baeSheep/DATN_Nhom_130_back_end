package com.DATN.DTO;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderDetailResponseDTO {
    private Integer orderDetailId;
    private Integer variantId;
    private String sku;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal; // trùng với DB
}
