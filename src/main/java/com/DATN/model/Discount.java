package com.DATN.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DISCOUNT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer discountId;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount_type")
    private String discountType; // e.g. "percent" or "amount"

    @Column(name = "value", precision = 12, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "min_order_value", precision = 12, scale = 2)
    private BigDecimal minOrderValue;

    private Integer usageLimit;

    private Integer perUserLimit;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "discount")
    private List<Order> orders = new ArrayList<>();
}

