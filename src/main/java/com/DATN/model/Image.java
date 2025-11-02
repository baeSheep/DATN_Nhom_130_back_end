package com.DATN.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "IMAGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    private String imageUrl;
//    private String thumbUrl;
    private Boolean isPrimary;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
