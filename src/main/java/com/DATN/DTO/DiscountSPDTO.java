package com.DATN.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DiscountSPDTO {
    private Integer discountspId;
    private Integer productId;
    private String discountType;
    private BigDecimal value;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
}
