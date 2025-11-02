package com.DATN.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "[USER]")
public class Users {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userID;
    
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
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserPoint> userPoints = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Wishlist> wishlists = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Support> supports = new ArrayList<>();
    
    @Override
    public String toString() {
        return "Users{userID=" + userID + ", email='" + email + "'}";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userID, email);
    }
}