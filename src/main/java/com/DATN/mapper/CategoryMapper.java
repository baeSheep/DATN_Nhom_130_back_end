package com.DATN.mapper;

import java.util.Date;

import com.DATN.DTO.CategoryDTO;
import com.DATN.model.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryID());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreated_at());
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        Category category = new Category();
        category.setCategoryID(dto.getCategoryId());
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setCreated_at(dto.getCreatedAt() != null ? dto.getCreatedAt() : new Date());
        return category;
    }
}
