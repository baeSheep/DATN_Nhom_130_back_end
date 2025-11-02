package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Authorities;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
	
}