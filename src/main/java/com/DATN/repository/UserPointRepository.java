package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.UserPoint;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Integer> {

}
