package com.DATN.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SUPPORT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supportId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private String subject;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String message;

    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
