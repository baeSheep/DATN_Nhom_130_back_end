package com.DATN.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DATN.DTO.CategoryDTO;
import com.DATN.mapper.CategoryMapper;
import com.DATN.model.Category;
import com.DATN.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	// Lấy toàn bộ danh mục
	public List<CategoryDTO> getAllCategories(){
		return categoryRepository.findAll()
				.stream()
				.map(CategoryMapper::toDTO)
				.collect(Collectors.toList());
	}
	// Lấy doanh mục theo id
	public CategoryDTO getCategoryById(Integer id) {
		return categoryRepository.findById(id)
				.map(CategoryMapper::toDTO)
				.orElse(null);
	}
	// Thêm danh mục mới
	public CategoryDTO createCategory(CategoryDTO dto) {
		Category category = CategoryMapper.toEntity(dto);
		Category saved = categoryRepository.save(category);
		return CategoryMapper.toDTO(saved);
	}
	// Cập nhật danh mục
	public CategoryDTO updateCategory(Integer id, CategoryDTO dto) {
		if(!categoryRepository.existsById(id)) return null;
		
		Category category = CategoryMapper.toEntity(dto);
		category.setCategoryID(id);
		Category updated = categoryRepository.save(category);
		return CategoryMapper.toDTO(updated);
	}
	// Xóa danh mục
	public boolean deleteCategory(Integer id) {
		if(categoryRepository.existsById(id)) {
			categoryRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
