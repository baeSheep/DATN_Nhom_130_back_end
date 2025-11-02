package com.DATN.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DELIVERY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliveryId;

    @Column(name = "shipping_provider")
    private String shippingProvider; // GHTK, GHN, VNPost,...

    @Column(name = "tracking_number", unique = true)
    private String trackingNumber; // Mã tra cứu vận đơn

    @Column(name = "shipping_fee", precision = 10, scale = 2)
    private BigDecimal shippingFee;

    @Column(name = "delivery_status")
    private String deliveryStatus; // preparing / shipping / delivered / canceled

    @Column(name = "estimated_date")
    private LocalDate estimatedDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "delivery")
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
}
