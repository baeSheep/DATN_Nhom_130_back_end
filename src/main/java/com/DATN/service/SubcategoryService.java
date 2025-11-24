package com.DATN.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DATN.DTO.SubcategoryDTO;
import com.DATN.mapper.SubcategoryMapper;
import com.DATN.model.Category;
import com.DATN.model.Subcategory;
import com.DATN.repository.CategoryRepository;
import com.DATN.repository.SubcategoryRepository;

@Service
public class SubcategoryService {
	@Autowired
	private SubcategoryRepository subRepo;
	
	 @Autowired
	    private CategoryRepository categoryRepo;

	    // Lấy tất cả
	    public List<SubcategoryDTO> findAll() {
	        return subRepo.findAll().stream()
	                .map(SubcategoryMapper::toDTO)
	                .collect(Collectors.toList());
	    }

	    // Lấy theo ID
	    public SubcategoryDTO findById(Integer id) {
	        return subRepo.findById(id)
	                .map(SubcategoryMapper::toDTO)
	                .orElse(null);
	    }

	    // Lấy theo category cha
	    public List<SubcategoryDTO> findByCategory(Integer categoryId) {
	        return subRepo.findByCategoryCategoryID(categoryId).stream()
	                .map(SubcategoryMapper::toDTO)
	                .collect(Collectors.toList());
	    }

	    // Tạo mới
	    public SubcategoryDTO create(SubcategoryDTO dto) {
	        Subcategory entity = SubcategoryMapper.toEntity(dto);

	        // gán category
	        if (dto.getCategoryId() != null) {
	            Category c = categoryRepo.findById(dto.getCategoryId()).orElse(null);
	            entity.setCategory(c);
	        }

	        entity = subRepo.save(entity);
	        return SubcategoryMapper.toDTO(entity);
	    }

	    // Cập nhật
	    public SubcategoryDTO update(Integer id, SubcategoryDTO dto) {
	        Subcategory entity = subRepo.findById(id).orElse(null);
	        if (entity == null) return null;

	        entity.setSubcategoryName(dto.getSubcategoryName());
	        entity.setCreated_at(dto.getCreatedAt() != null ? dto.getCreatedAt() : new Date());

	        if (dto.getCategoryId() != null) {
	            Category c = categoryRepo.findById(dto.getCategoryId()).orElse(null);
	            entity.setCategory(c);
	        }

	        entity = subRepo.save(entity);
	        return SubcategoryMapper.toDTO(entity);
	    }

	    // Xóa
	    public void delete(Integer id) {
	        subRepo.deleteById(id);
	    }
}
