package com.DATN.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.DATN.DTO.ProductDTO;
import com.DATN.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200","http://127.0.0.1:5500","http://localhost:8080"})

public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        ProductDTO product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO dto, BindingResult bindingResult){
    	// nếu có lỗi validate
    	if(bindingResult.hasErrors()) {
    		// Lấy danh sách lỗi từ annotation
    		List<String> errors = bindingResult.getFieldErrors().stream()
    				.map(error -> String.format("Trường '%s' %s", error.getField(), error.getDefaultMessage()))
    				.toList();
    		// Trả về danh sách lỗi
    		return ResponseEntity.badRequest().body(errors);
    	}
    	// nếu không có lỗi thì gọi service
    	ProductDTO create = productService.createProduct(dto);
    	return ResponseEntity.ok(create);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,@Valid @RequestBody ProductDTO dto, BindingResult bindingResult){
    	if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> String.format("Trường '%s' %s", error.getField(), error.getDefaultMessage()))
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }
    	ProductDTO updated = productService.updateProduct(id, dto);
    	return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Integer id){
    	boolean deleted = productService.softDelete(id);
    	Map<String, String> response = new HashMap<>();
    	if(deleted) {
    		response.put("message", "Đã xóa sản phẩm (ẩn) thành công!");
    		return ResponseEntity.ok(response);
    	}else {
    		response.put("message", "Không tìm thấy sản phẩm để xóa");
    		return ResponseEntity.status(404).body(response);
    	}
    	
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