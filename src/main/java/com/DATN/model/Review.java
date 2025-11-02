package com.DATN.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "REVIEW")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private Users users;

    private Integer rating; // 1-5
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}
