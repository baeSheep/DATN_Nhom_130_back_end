package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
