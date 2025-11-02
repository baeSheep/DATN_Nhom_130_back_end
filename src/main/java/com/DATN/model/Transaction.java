package com.DATN.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "transaction_code", unique = true)
    private String transactionCode;

    @Column(name = "transaction_status")
    private String transactionStatus; // pending, success, failed...

    private BigDecimal amount;

    private String paymentGateway; // e.g. VNPay, MoMo, COD

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime paidAt;
}
