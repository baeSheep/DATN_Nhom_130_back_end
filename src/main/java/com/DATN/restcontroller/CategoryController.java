package com.DATN.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.CategoryDTO;
import com.DATN.service.CategoryService;


@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200","http://127.0.0.1:5500"})
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	// Lấy tât cả cat
	@GetMapping
	public List<CategoryDTO> getAllCategories(){
		return categoryService.getAllCategories();
	}
	
	//Lấy cate theo ID
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
		CategoryDTO dto = categoryService.getCategoryById(id);
		return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
	}
	
	//Thêm mới
	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {
		CategoryDTO created = categoryService.createCategory(dto);
		return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
	}
	
	// Cập nhật
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO dto) {
		CategoryDTO updated = categoryService.updateCategory(id, dto);
		return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
	}
	
	// Xóa
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
		boolean deleted = categoryService.deleteCategory(id);
		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
