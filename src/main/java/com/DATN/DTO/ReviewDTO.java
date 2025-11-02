package com.DATN.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private Integer productId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}