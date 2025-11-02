package com.DATN.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findByCategory_CategoryID(Integer categoryId);
    List<Product> findBySubcategory_SubcategoryID(Integer subcategoryId);
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
	
}
