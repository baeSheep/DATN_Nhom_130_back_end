package com.DATN.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreateRequest {
    private Integer userId;
    private Integer addressId;
    private Integer deliveryId;   // ID phương thức giao hàng
    private Integer discountId;   // ID mã giảm giá (nếu có)
    private String paymentMethod;
    private String note;
    private List<OrderDetailCreateRequest> items;
}
