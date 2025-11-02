package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

}
