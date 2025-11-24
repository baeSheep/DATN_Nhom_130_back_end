package com.DATN.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Integer orderDetailId;
    private Integer variantId;      // ID của biến thể
    private String productName;     // Tên sản phẩm
    private String sku;             // Thêm để hiển thị nếu cần
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;    // ⚠️ Phải trùng với bảng ORDER_DETAIL
}
