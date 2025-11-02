package com.DATN.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTHORITIES")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Integer authorityId;

    // FK đến Users (NOT NULL theo DB)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-authorities")
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    // FK đến Employee (NOT NULL theo DB)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("employee-authorities")
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // FK đến Role (NOT NULL theo DB)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("role-authorities")
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;
}