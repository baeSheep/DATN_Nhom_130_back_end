package com.DATN.restcontroller;

import com.DATN.DTO.DiscountSPDTO;
import com.DATN.service.DiscountProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount-products")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class DiscountProductController {

    @Autowired
    private DiscountProductService discountProductService;

    @GetMapping
    public ResponseEntity<List<DiscountSPDTO>> getAllDiscounts() {
        return ResponseEntity.ok(discountProductService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountSPDTO> getDiscountById(@PathVariable Integer id) {
        DiscountSPDTO dto = discountProductService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DiscountSPDTO> createDiscount(@RequestBody DiscountSPDTO dto) {
        return ResponseEntity.ok(discountProductService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountSPDTO> updateDiscount(
            @PathVariable Integer id,
            @RequestBody DiscountSPDTO dto) {
        return ResponseEntity.ok(discountProductService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        discountProductService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
