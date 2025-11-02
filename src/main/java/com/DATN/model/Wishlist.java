package com.DATN.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WISHLIST")
public class Wishlist {
    @EmbeddedId
    private WishlistId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "product_id")
    private Product product;
}