package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Support;

@Repository
public interface SupportRepository extends JpaRepository<Support, Integer> {

}
