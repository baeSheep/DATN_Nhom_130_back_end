package com.DATN.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetailCreateRequest {
    private Integer variantId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
