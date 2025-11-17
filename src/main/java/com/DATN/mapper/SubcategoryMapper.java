package com.DATN.mapper;

import java.util.Date;

import com.DATN.DTO.SubcategoryDTO;
import com.DATN.model.Category;
import com.DATN.model.Subcategory;

public class SubcategoryMapper {
    public static SubcategoryDTO toDTO(Subcategory sub) {
        if (sub == null) return null;
        SubcategoryDTO dto = new SubcategoryDTO();
        dto.setSubcategoryId(sub.getSubcategoryId());
        dto.setSubcategoryName(sub.getSubcategoryName());
        dto.setCategoryId(sub.getCategory() != null ? sub.getCategory().getCategoryID() : null);
        dto.setCreatedAt(sub.getCreated_at());
        return dto;
    }

    public static Subcategory toEntity(SubcategoryDTO dto) {
        if (dto == null) return null;
        Subcategory sub = new Subcategory();
        sub.setSubcategoryId(dto.getSubcategoryId());
        sub.setSubcategoryName(dto.getSubcategoryName());
        
        sub.setCreated_at(dto.getCreatedAt() != null ? dto.getCreatedAt() : new Date());
        if (dto.getCategoryId() != null) {
        	Category category = new Category();
        	category.setCategoryID(dto.getCategoryId());
			sub.setCategory(category);
		}else {
			sub.setCategory(null);
		}
        
        return sub;
    }
}