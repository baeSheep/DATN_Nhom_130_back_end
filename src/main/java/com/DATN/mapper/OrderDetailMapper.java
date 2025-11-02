package com.DATN.mapper;

import com.DATN.DTO.OrderDetailDTO;
import com.DATN.model.OrderDetail;

public class OrderDetailMapper {
	public static OrderDetailDTO toDTO(OrderDetail item) {
        if (item == null) return null;
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderDetailId(item.getOrderItemId());
        dto.setVariantId(item.getVariant().getVariantId());
        dto.setProductName(item.getVariant().getProduct().getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }

    public static OrderDetail toEntity(OrderDetailDTO dto) {
        if (dto == null) return null;
        OrderDetail item = new OrderDetail();
        item.setOrderItemId(dto.getOrderDetailId());
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());
        item.setTotalPrice(dto.getTotalPrice());
        return item;
    }
}
