package com.DATN.DTO;

import lombok.Data;

@Data
public class CheckoutRequest {
private Integer userId;
private Integer addressId;
private String customerName;
private String customerEmail;
private String customerPhone;
private String detailAddress;
private String province;
private String district;
private String ward;
private String couponCode;
private String paymentMethod;
}


