package com.DATN.restcontroller;

import com.DATN.DTO.OrderDetailResponseDTO;
import com.DATN.DTO.OrderResponseDTO;
import com.DATN.DTO.OrderCreateRequest;
import com.DATN.model.Order;
import com.DATN.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    // Tạo đơn — chỉnh chuẩn REST + trả DTO
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreateRequest request) {
        Order created = orderService.createOrderFromDTO(request);
        return ResponseEntity.ok(orderService.getOrderByIdDTO(created.getOrderId()));
    }

    // Lấy tất cả đơn (DTO)
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Lấy đơn theo ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Integer orderId) {
        OrderResponseDTO dto = orderService.getOrderByIdDTO(orderId);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    // Lấy chi tiết đơn
    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<OrderDetailResponseDTO>> getOrderDetails(@PathVariable Integer orderId) {
        OrderResponseDTO dto = orderService.getOrderByIdDTO(orderId);
        if (dto == null) return ResponseEntity.notFound().build();
        List<OrderDetailResponseDTO> details = dto.getDetails();
        if (details == null) details = Collections.emptyList();
        return ResponseEntity.ok(details);
    }

    // Cập nhật trạng thái
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        OrderResponseDTO updatedDto = orderService.getOrderByIdDTO(orderId);
        if (updatedDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedDto);
    }
}
