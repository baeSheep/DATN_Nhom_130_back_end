package com.DATN.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class SubcategoryDTO {
    private Integer subcategoryId;
    private String subcategoryName;
    private Integer categoryId;
    private Date createdAt;
}
