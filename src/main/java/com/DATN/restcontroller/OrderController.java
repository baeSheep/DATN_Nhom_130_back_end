package com.DATN.restcontroller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DATN.DTO.OrderDTO;
import com.DATN.mapper.OrderMapper;
import com.DATN.model.Order;
import com.DATN.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    // 1. Lấy tất cả đơn hàng của user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Integer userId) {
        List<Order> orders = orderRepository.findByUsers_UserID(userId);
        List<OrderDTO> dtos = orders.stream()
                                    .map(OrderMapper::toDTO)
                                    .toList();
        return ResponseEntity.ok(dtos);
    }

    // 2. Lấy chi tiết một đơn hàng
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order không tồn tại"));
        OrderDTO dto = OrderMapper.toDTO(order);
        return ResponseEntity.ok(dto);
    }
}

