package com.DATN.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean gender;
    private String avatar;
    private Boolean verified;
    private Float totalPoint;
    private LocalDateTime createdAt;
}