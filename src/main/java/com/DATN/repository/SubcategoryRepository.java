package com.DATN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

}
