package com.DATN.repository;

import com.DATN.model.Order;
import com.DATN.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUsers(Users user);

    List<Order> findByStatus(String status);

    List<Order> findByUsersAndStatus(Users user, String status);
}
