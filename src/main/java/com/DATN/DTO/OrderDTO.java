package com.DATN.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Integer orderId;
    private Integer userId;
    private String orderStatus;
    private BigDecimal totalAmount;
    private BigDecimal deliveryFee;
    private LocalDateTime createdAt;
    private Integer discountId;
    private Integer deliveryId;
    private List<OrderDetailDTO> items;
}