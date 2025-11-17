package com.DATN.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.SubcategoryDTO;
import com.DATN.service.SubcategoryService;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200","http://127.0.0.1:5500"})
public class SubcategoryController {
	@Autowired
    private SubcategoryService subService;

    @GetMapping
    public List<SubcategoryDTO> getAll() {
        return subService.findAll();
    }

    @GetMapping("/{id}")
    public SubcategoryDTO getOne(@PathVariable Integer id) {
        return subService.findById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<SubcategoryDTO> getByCategory(@PathVariable Integer categoryId) {
        return subService.findByCategory(categoryId);
    }

    @PostMapping
    public SubcategoryDTO create(@RequestBody SubcategoryDTO dto) {
        return subService.create(dto);
    }

    @PutMapping("/{id}")
    public SubcategoryDTO update(@PathVariable Integer id, @RequestBody SubcategoryDTO dto) {
        return subService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        subService.delete(id);
    }
	
}
