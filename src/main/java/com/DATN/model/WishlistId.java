package com.DATN.model;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistId implements Serializable {

    @Column(name = "userID")
    private Integer userID;

    @Column(name = "productID")
    private String productID;

}
