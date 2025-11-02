package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
