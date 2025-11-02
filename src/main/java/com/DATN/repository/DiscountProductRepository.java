package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.DiscountProduct;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProduct, Integer> {
}
