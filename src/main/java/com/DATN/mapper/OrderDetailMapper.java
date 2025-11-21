package com.DATN.mapper;

import com.DATN.DTO.OrderDetailResponseDTO;
import com.DATN.model.OrderDetail;

import java.math.BigDecimal;

public class OrderDetailMapper {

    public static OrderDetailResponseDTO toDTO(OrderDetail item) {
        if (item == null) return null;

        OrderDetailResponseDTO dto = new OrderDetailResponseDTO();

        dto.setOrderDetailId(item.getOrderDetailId());

        // Variant
        if (item.getVariant() != null) {
            dto.setVariantId(item.getVariant().getVariantId());
            dto.setSku(item.getVariant().getSku());

            if (item.getVariant().getProduct() != null) {
                dto.setProductName(item.getVariant().getProduct().getProductName());
            }
        }

        // Quantity – Unit Price
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());

        // ⭐ Subtotal FIX: lấy từ DB, nếu null thì tự tính
        if (item.getSubtotal() != null) {
            dto.setSubtotal(item.getSubtotal());
        } else {
            BigDecimal calculated = item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            dto.setSubtotal(calculated);
        }

        return dto;
    }
}
