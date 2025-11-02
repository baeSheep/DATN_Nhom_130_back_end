package com.DATN.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-addresses")
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "receiver_name", nullable = false, length = 150)
    private String receiverName;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "province", nullable = false, length = 100)
    private String province;
    
    @Column(name = "district", nullable = false, length = 100)
    private String district;
    
    @Column(name = "ward", length = 100)
    private String ward;
    
    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress;
    
    @Column(name = "is_default")
    private Boolean isDefault = false;
    
    @JsonIgnore
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
