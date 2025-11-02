package com.DATN.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.DATN.DTO.ProductDTO;
import com.DATN.model.Image;
import com.DATN.model.Product;

import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDTO toDTO(Product entity) {
        if (entity == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setProductId(entity.getProductId());
        dto.setProductName(entity.getProductName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setImageUrls(entity.getImages() != null
        	    ? entity.getImages().stream()
        	          .map(Image::getImageUrl)
        	          .collect(Collectors.toList())
        	    : new ArrayList<>());

        
        if (entity.getCategory() != null)
            dto.setCategory(CategoryMapper.toDTO(entity.getCategory()));
        if (entity.getSubcategory() != null)
            dto.setSubcategory(SubcategoryMapper.toDTO(entity.getSubcategory()));

        // Map variants
        if (entity.getVariants() != null)
            dto.setVariants(entity.getVariants()
                    .stream()
                    .map(ProductVariantMapper::toDTO)
                    .collect(Collectors.toList()));

        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product entity = new Product();
        entity.setProductId(dto.getProductId());
        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
