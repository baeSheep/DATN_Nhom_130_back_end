package com.DATN.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DISCOUNT_SP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discountsp_id")
    private Integer discountProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Sản phẩm được giảm giá

    @Column(name = "discount_type", length = 10, nullable = false)
    private String discountType; // "percent" hoặc "amount"

    @Column(name = "value", precision = 12, scale = 2, nullable = false)
    private BigDecimal value; // Giá trị giảm

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}

