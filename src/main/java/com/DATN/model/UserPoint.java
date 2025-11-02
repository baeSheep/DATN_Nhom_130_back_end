package com.DATN.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_POINT")
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id")
    private Integer userPointID;
    
    @Column(name = "point", nullable = false)
    private Integer point;
    
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private java.util.Date date = new java.util.Date();
    
    @Column(name = "status", length = 50)
    private String status = "active";
    
    // FK đến Users (NOT NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-userpoints")
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    // FK đến Order (NULLABLE - chỉ set khi điểm từ đơn hàng)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("order-userpoints")
    @JoinColumn(name = "order_id")
    private Order order;
}

