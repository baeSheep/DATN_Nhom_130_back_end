package com.DATN.service;

import com.DATN.DTO.DiscountSPDTO;
import com.DATN.model.DiscountProduct;
import com.DATN.model.Product;
import com.DATN.repository.DiscountProductRepository;
import com.DATN.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountProductService {

    @Autowired
    private DiscountProductRepository discountProductRepository;

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả
    public List<DiscountSPDTO> getAll() {
        return discountProductRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy theo ID
    public DiscountSPDTO getById(Integer id) {
        Optional<DiscountProduct> discountOpt = discountProductRepository.findById(id);
        return discountOpt.map(this::convertToDTO).orElse(null);
    }

    // Thêm mới
    public DiscountSPDTO create(DiscountSPDTO dto) {
        DiscountProduct discount = convertToEntity(dto);
        discount = discountProductRepository.save(discount);
        return convertToDTO(discount);
    }

    // Cập nhật
    public DiscountSPDTO update(Integer id, DiscountSPDTO dto) {
        DiscountProduct existing = discountProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
        existing.setDiscountType(dto.getDiscountType());
        existing.setValue(dto.getValue());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setIsActive(dto.getActive());
        DiscountProduct updated = discountProductRepository.save(existing);
        return convertToDTO(updated);
    }

    // Xóa
    public void delete(Integer id) {
        discountProductRepository.deleteById(id);
    }

    // --- Helper ---
    private DiscountSPDTO convertToDTO(DiscountProduct discount) {
        DiscountSPDTO dto = new DiscountSPDTO();
        dto.setDiscountspId(discount.getDiscountProductId());
        dto.setProductId(discount.getProduct().getProductId());
        dto.setDiscountType(discount.getDiscountType());
        dto.setValue(discount.getValue());
        dto.setStartDate(discount.getStartDate());
        dto.setEndDate(discount.getEndDate());
        dto.setActive(discount.getIsActive());
        return dto;
    }

    private DiscountProduct convertToEntity(DiscountSPDTO dto) {
        DiscountProduct discount = new DiscountProduct();
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        discount.setProduct(product);
        discount.setDiscountType(dto.getDiscountType());
        discount.setValue(dto.getValue());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setIsActive(dto.getActive());
        return discount;
    }
}
