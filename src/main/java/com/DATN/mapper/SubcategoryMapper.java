package com.DATN.mapper;

import com.DATN.DTO.SubcategoryDTO;
import com.DATN.model.Subcategory;

public class SubcategoryMapper {
    public static SubcategoryDTO toDTO(Subcategory sub) {
        if (sub == null) return null;
        SubcategoryDTO dto = new SubcategoryDTO();
        dto.setSubcategoryId(sub.getSubcategoryID());
        dto.setSubcategoryName(sub.getSubcategoryName());
        dto.setCategoryId(sub.getCategory() != null ? sub.getCategory().getCategoryID() : null);
        return dto;
    }

    public static Subcategory toEntity(SubcategoryDTO dto) {
        if (dto == null) return null;
        Subcategory sub = new Subcategory();
        sub.setSubcategoryID(dto.getSubcategoryId());
        sub.setSubcategoryName(dto.getSubcategoryName());
        return sub;
    }
}