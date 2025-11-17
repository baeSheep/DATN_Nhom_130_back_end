package com.DATN.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDTO {
    private Integer variantId;
    private String sku;
   // @NotBlank(message = "Kích cỡ không được để trống")
    private String size;
   // @NotBlank(message = "Màu sắc không được để trống")
    private String color;

    
   // @Positive(message = "Giá phải lớn hơn 0")
    private BigDecimal price;
    private BigDecimal costPrice;
    //@Min(value = 0, message = "Số lượng tồn không được âm")
    private Integer stock;
    private BigDecimal weight;
    private LocalDateTime createdAt;
    
//    private Integer variantId;
//    private String sku;
//    private String size;
//    private String color;
//    private BigDecimal price;
//    private BigDecimal costPrice;
//    private Integer stockQuantity;
//    private BigDecimal weight;
//    private LocalDateTime createdAt;
}
