package com.DATN.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT_VARIANT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer variantId;

    private String sku;
    private String size;
    private String color;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Integer stockQuantity;
    private BigDecimal weight;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}