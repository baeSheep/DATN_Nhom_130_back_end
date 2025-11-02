package com.DATN.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "[ORDER]")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private BigDecimal totalAmount;

    private BigDecimal deliveryFee;  // ✅ Phí giao hàng (cập nhật mới)

    private Integer usedPoints;

    private String paymentMethod;

    private String status;

    private String note;

    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> details = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Transaction transaction;
}
