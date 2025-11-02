package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
