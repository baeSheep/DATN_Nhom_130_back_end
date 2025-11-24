package com.DATN.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.DATN.model.Category;
import com.DATN.model.Subcategory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer productId;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không vượt quá 255 ký tự")
    private String productName;
    
    private String slug;
    @Size(max = 2000, message = "Mô tả sản phẩm quá dài (tối đa 2000 ký tự)")
    private String description;
    @NotBlank(message = "Trạng thái không được để trống")
    private String status;
    
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    @NotNull(message = "Danh mục không được để trống")
//    private CategoryDTO category;
//    
//    private SubcategoryDTO subcategory;
    
    private Integer categoryId;
    private Integer subcategoryId;
    @NotEmpty(message = "Phải có ít nhất một ảnh sản phẩm")
    private List<ImageDTO> imageUrls;
    @NotEmpty(message = "Phải có ít nhất một biến thể sản phẩm")
    private SubcategoryDTO subcategory;
    private List<String> imageUrls;
    private List<ProductVariantDTO> variants;
}
