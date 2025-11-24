package com.DATN.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DATN.DTO.ProductDTO;
import com.DATN.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})

public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        ProductDTO product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/subcategory/{id}")
    public List<ProductDTO> getProductsBySubcategory(@PathVariable Integer id) {
        return productService.getProductsBySubcategory(id);
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }
}