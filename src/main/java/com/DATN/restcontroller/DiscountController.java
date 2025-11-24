package com.DATN.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.model.Discount;
import com.DATN.repository.DiscountRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/discount")
@RequiredArgsConstructor
public class DiscountController {


private final DiscountRepository discountRepository;


@GetMapping("/apply/{code}")
public Discount apply(@PathVariable String code) {
return discountRepository.findByCodeAndActiveTrue(code)
.orElseThrow(() -> new RuntimeException("Mã không hợp lệ"));
}
}
