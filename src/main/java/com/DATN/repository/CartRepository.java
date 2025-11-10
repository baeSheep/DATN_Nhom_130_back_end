package com.DATN.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByUser_UserID(Integer userId);
	
	 // Cart của guest theo sessionId
    Optional<Cart> findBySessionId(String sessionId);
}
