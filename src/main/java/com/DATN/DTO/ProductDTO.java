package com.DATN.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer productId;
    private String productName;
    private String description;
    private String status;
    private CategoryDTO category;
    private SubcategoryDTO subcategory;
    private List<String> imageUrls;
    private List<ProductVariantDTO> variants;
}
