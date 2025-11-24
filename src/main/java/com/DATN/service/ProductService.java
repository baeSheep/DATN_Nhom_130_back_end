package com.DATN.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DATN.DTO.ProductDTO;
import com.DATN.DTO.ProductVariantDTO;
import com.DATN.mapper.ProductMapper;
import com.DATN.model.Category;
import com.DATN.model.Image;
import com.DATN.model.Product;
import com.DATN.model.ProductVariant;
import com.DATN.model.Subcategory;
import com.DATN.repository.CategoryRepository;
import com.DATN.repository.ProductRepository;
import com.DATN.repository.SubcategoryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;
    
    public List<ProductDTO> getAllProducts() {
        return productRepository.findByDeletedFalse()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }
    
    public ProductDTO getProductById(Integer id) {
        return ProductMapper.toDTO(
        		productRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"))
        		);
    }


    public ProductDTO createProduct(ProductDTO dto) {
    	Product product = ProductMapper.toEntity(dto); // chuyển DTO -> Entity
    	
    	product.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        product.setSubcategory(subcategoryRepository.findById(dto.getSubcategoryId()).orElse(null));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        
     // Map Variants
        if (dto.getVariants() != null) {
            dto.getVariants().forEach(v -> {
                ProductVariant pv = ProductVariant.builder()
                        .sku(v.getSku())
                        .size(v.getSize())
                        .color(v.getColor())
                        .price(v.getPrice())
                        .costPrice(v.getCostPrice())
                        .stockQuantity(v.getStock())
                        .weight(v.getWeight())
                        .createdAt(LocalDateTime.now())
                        .product(product)
                        .build();
                product.getVariants().add(pv);
            });
        }
       
     // Map Images
        if (dto.getImageUrls() != null) {
            dto.getImageUrls().forEach(img -> {
                Image image = Image.builder()
                        .imageUrl(img.getImageUrl())
                        .isPrimary(img.getIsPrimary())
                        .position(img.getPosition())
                        .product(product)
                        .build();
                product.getImages().add(image);
            });
        }
    	
        return ProductMapper.toDTO(productRepository.save(product));

    }
    
    public ProductDTO updateProduct(Integer id, ProductDTO dto) {
    	Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
    	
    	product.setProductName(dto.getProductName());
        product.setSlug(dto.getSlug());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        product.setUpdatedAt(LocalDateTime.now());

        product.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        product.setSubcategory(subcategoryRepository.findById(dto.getSubcategoryId()).orElse(null));

        // Xóa toàn bộ variants & images cũ (vì orphanRemoval = true)
        product.getVariants().clear();
        product.getImages().clear();
        
     // Thêm mới variants
        if (dto.getVariants() != null) {
            dto.getVariants().forEach(v -> {
                ProductVariant pv = ProductVariant.builder()
                        .sku(v.getSku())
                        .size(v.getSize())
                        .color(v.getColor())
                        .price(v.getPrice())
                        .costPrice(v.getCostPrice())
                        .stockQuantity(v.getStock())
                        .weight(v.getWeight())
                        .createdAt(LocalDateTime.now())
                        .product(product)
                        .build();
                product.getVariants().add(pv);
            });
        }
        
     // Thêm mới images
        if (dto.getImageUrls() != null) {
            dto.getImageUrls().forEach(img -> {
                Image image = Image.builder()
                        .imageUrl(img.getImageUrl())
                        .isPrimary(img.getIsPrimary())
                        .position(img.getPosition())
                        .product(product)
                        .build();
                product.getImages().add(image);
            });
        }

        return ProductMapper.toDTO(productRepository.save(product));
    }
    
    public boolean softDelete(Integer id) {
        Product product = productRepository.findById(id)
                .orElse(null);

        if (product == null) return false;

        product.setDeleted(true);
        productRepository.save(product);
        return true;
    }

    
    public void restore(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        product.setDeleted(false);
        productRepository.save(product);
    }
    
    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategory_CategoryID(categoryId)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySubcategory(Integer subcategoryId) {
        return productRepository.findBySubcategory_SubcategoryId(subcategoryId)
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
