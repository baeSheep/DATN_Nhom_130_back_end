package com.DATN.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DATN.DTO.ProductDTO;
import com.DATN.mapper.ProductMapper;
import com.DATN.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductDTO getProductById(Integer id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDTO)
                .orElse(null);
    }

   
    
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

   

    public List<ProductDTO> getProductsBySubcategory(Integer subcategoryId) {
        return productRepository.findBySubcategory_SubcategoryID(subcategoryId)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
