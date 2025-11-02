package com.DATN.mapper;

import java.util.stream.Collectors;

import com.DATN.DTO.OrderDTO;
import com.DATN.model.Order;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUsers() != null ? order.getUsers().getUserID() : null);
        dto.setOrderStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setCreatedAt(order.getOrderDate());
        dto.setDiscountId(order.getDiscount() != null ? order.getDiscount().getDiscountId() : null);
        dto.setDeliveryId(order.getDelivery() != null ? order.getDelivery().getDeliveryId() : null);
        if (order.getDetails() != null) {
            dto.setItems(order.getDetails()
                .stream()
                .map(OrderDetailMapper::toDTO)
                .collect(Collectors.toList()));
        }
        return dto;
    }

    public static Order toEntity(OrderDTO dto) {
        if (dto == null) return null;
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
        order.setStatus(dto.getOrderStatus());
        order.setTotalAmount(dto.getTotalAmount());
        order.setDeliveryFee(dto.getDeliveryFee());
        order.setOrderDate(dto.getCreatedAt());
        return order;
    }
}
