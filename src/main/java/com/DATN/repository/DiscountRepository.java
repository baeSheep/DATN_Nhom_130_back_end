package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
