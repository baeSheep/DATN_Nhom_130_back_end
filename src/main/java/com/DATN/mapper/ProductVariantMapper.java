package com.DATN.mapper;

import com.DATN.DTO.ProductVariantDTO;
import com.DATN.model.ProductVariant;

public class ProductVariantMapper {
    public static ProductVariantDTO toDTO(ProductVariant variant) {
        if (variant == null) return null;
        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setVariantId(variant.getVariantId());
        dto.setColor(variant.getColor());
        dto.setColor_code(variant.getColor_code());
        dto.setSize(variant.getSize());
        dto.setPrice(variant.getPrice());
        dto.setStock(variant.getStockQuantity());
        return dto;
    }

    public static ProductVariant toEntity(ProductVariantDTO dto) {
        if (dto == null) return null;
        ProductVariant variant = new ProductVariant();
        variant.setVariantId(dto.getVariantId());
        variant.setColor(dto.getColor());
        variant.setColor_code(dto.getColor_code());
        variant.setSize(dto.getSize());
        variant.setPrice(dto.getPrice());
        variant.setStockQuantity(dto.getStock());
        return variant;
    }
}