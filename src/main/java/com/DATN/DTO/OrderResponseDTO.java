package com.DATN.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDTO {
    private Integer orderId;
    private Integer userId;         // chỉ id user, không lồng user object
    private Integer addressId;
    private Integer deliveryId;
    private Integer discountId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private BigDecimal deliveryFee;
    private Integer usedPoints;
    private String paymentMethod;
    private String note;
    private List<OrderDetailResponseDTO> details;
}
