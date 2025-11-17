package com.DATN.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.DATN.DTO.ImageDTO;
import com.DATN.DTO.ProductDTO;
import com.DATN.DTO.ProductVariantDTO;
import com.DATN.model.Image;
import com.DATN.model.Product;
import com.DATN.model.ProductVariant;

import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDTO toDTO(Product entity) {
        if (entity == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setProductId(entity.getProductId());
        dto.setProductName(entity.getProductName());
        dto.setSlug(entity.getSlug());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setDeleted(entity.isDeleted());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getCategoryID() : null);
        dto.setSubcategoryId(entity.getSubcategory() != null ? entity.getSubcategory().getSubcategoryId() : null);
       
        dto.setImageUrls(
                entity.getImages().stream()
                    .map(img -> new ImageDTO(
                        img.getImageId(),
                        img.getImageUrl(),
                        img.getIsPrimary(),
                        img.getPosition()
                    )).toList()
            );

        dto.setVariants(
                entity.getVariants().stream()
                    .map(v -> new ProductVariantDTO(
                        v.getVariantId(),
                        v.getSku(),
                        v.getSize(),
                        v.getColor(),
                        v.getPrice(),
                        v.getCostPrice(),
                        v.getStockQuantity(),
                        v.getWeight(),
                        v.getCreatedAt()
                    )).toList()
            );
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product entity = new Product();
        entity.setProductId(dto.getProductId());
        entity.setProductName(dto.getProductName());
        entity.setSlug(dto.getSlug());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setDeleted(dto.isDeleted());;        
        
        return entity;
    }
}
