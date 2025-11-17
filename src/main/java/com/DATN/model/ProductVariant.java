package com.DATN.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Column(unique = false, nullable = true)
    private String sku; // SKU duy nhất cho mỗi biến thể
    
    private String size;
    private String color;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Integer stockQuantity;
    private BigDecimal weight;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
}