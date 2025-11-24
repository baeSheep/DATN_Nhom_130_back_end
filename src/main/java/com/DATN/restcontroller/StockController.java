package com.DATN.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.model.ProductVariant;
import com.DATN.repository.ProductVariantRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {


private final ProductVariantRepository variantRepository;


@GetMapping("/check/{variantId}/{quantity}")
public boolean checkStock(@PathVariable Integer variantId, @PathVariable Integer quantity){
ProductVariant v = variantRepository.findById(variantId).orElseThrow();
return v.getStockQuantity() >= quantity;
}
}