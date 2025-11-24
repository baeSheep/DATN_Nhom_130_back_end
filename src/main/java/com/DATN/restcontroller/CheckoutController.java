package com.DATN.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.CheckoutRequest;
import com.DATN.DTO.OrderDTO;
import com.DATN.service.CheckoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"}, allowCredentials = "true")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<OrderDTO> checkout(@RequestBody CheckoutRequest request) {
        OrderDTO order = checkoutService.processCheckout(request);
        return ResponseEntity.ok(order);
    }
}
