package com.DATN.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeID;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "first_name", length = 30)
    private String firstName;
    
    @Column(name = "last_name", length = 30)
    private String lastName;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Column(name = "avatar_url", length = 255)
    private String avatar;
    
    @Column(name = "gender", length = 10)
    private String gender;
    
    @Column(name = "dob")
    private LocalDate dob;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
    
    @Column(name = "verified")
    private Boolean verified = false;
    
    @Column(name = "login_attempts")
    private Integer loginAttempts = 0;
    
    @Column(name = "aes_key", columnDefinition = "NVARCHAR(MAX)")
    private String aesKey;
    
    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();
}