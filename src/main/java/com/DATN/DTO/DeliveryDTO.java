package com.DATN.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DeliveryDTO {
    private Integer deliveryId;
    private String shippingProvider;
    private String trackingNumber;
    private BigDecimal shippingFee;
    private String deliveryStatus;
    private LocalDate estimatedDate;
    private LocalDateTime createdAt;
}