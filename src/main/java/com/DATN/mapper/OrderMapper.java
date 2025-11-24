package com.DATN.mapper;

import com.DATN.DTO.OrderResponseDTO;
import com.DATN.DTO.OrderDetailResponseDTO;
import com.DATN.model.Order;

import java.util.Collections;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDTO toDTO(Order order) {
        if (order == null) return null;

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUsers() != null ? order.getUsers().getUserID() : null);
        dto.setAddressId(order.getAddress() != null ? order.getAddress().getAddressId() : null);
        dto.setDeliveryId(order.getDelivery() != null ? order.getDelivery().getDeliveryId() : null);
        dto.setDiscountId(order.getDiscount() != null ? order.getDiscount().getDiscountId() : null);

        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setUsedPoints(order.getUsedPoints());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setNote(order.getNote());

        // ⭐ SAFETY FOR DETAILS LIST
        if (order.getDetails() != null && !order.getDetails().isEmpty()) {
            dto.setDetails(
                    order.getDetails().stream()
                            .map(det -> (OrderDetailResponseDTO) OrderDetailMapper.toDTO(det))
                            .collect(Collectors.toList())
            );
        } else {
            dto.setDetails(Collections.emptyList());
        }

        return dto;
    }
}
