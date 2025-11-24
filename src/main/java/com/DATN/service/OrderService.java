package com.DATN.service;

import com.DATN.DTO.OrderCreateRequest;
import com.DATN.DTO.OrderDetailCreateRequest;
import com.DATN.DTO.OrderDetailResponseDTO;
import com.DATN.DTO.OrderResponseDTO;
import com.DATN.model.*;
import com.DATN.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductVariantRepository productVariantRepository;

    private final BigDecimal DEFAULT_DELIVERY_FEE = new BigDecimal("30000");

    /** ==========================================================
     *  TẠO ĐƠN HÀNG (CODE CŨ CỦA BẠN, KHÔNG ĐỤNG)
     *  ========================================================== */
    @Transactional
    public Order createOrderFromDTO(OrderCreateRequest request) {

        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Order order = new Order();
        order.setUsers(user);
        order.setAddress(address);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setNote(request.getNote());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderDetailCreateRequest itemReq : request.getItems()) {

            ProductVariant variant = productVariantRepository.findById(itemReq.getVariantId())
                    .orElseThrow(() -> new RuntimeException("Variant not found: " + itemReq.getVariantId()));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setVariant(variant);
            detail.setQuantity(itemReq.getQuantity());
            detail.setUnitPrice(itemReq.getUnitPrice());

            BigDecimal subtotal = itemReq.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            detail.setSubtotal(subtotal);
            totalAmount = totalAmount.add(subtotal);

            details.add(detail);
        }

        order.setDetails(details);
        order.setDeliveryFee(DEFAULT_DELIVERY_FEE);
        totalAmount = totalAmount.add(DEFAULT_DELIVERY_FEE);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.saveAll(details);

        return savedOrder;
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public Order updateOrderStatus(Integer id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    /** ==========================================================
     *  ⭐⭐ TRẢ VỀ DTO CHO REST CONTROLLER ⭐⭐
     *  ========================================================== */

    // Lấy tất cả đơn hàng (trả về DTO)
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Lấy 1 đơn theo ID (trả DTO)
    public OrderResponseDTO getOrderByIdDTO(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /** ==========================================================
     *  ⭐⭐ CHUYỂN ENTITY → DTO (CHỖ BỊ THIẾU CẦN SỬA) ⭐⭐
     *  ========================================================== */
    private OrderResponseDTO convertToDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setOrderId(order.getOrderId());

        // Lấy userId, addressId, deliveryId, discountId
        dto.setUserId(order.getUsers() != null ? order.getUsers().getUserID() : null);
        dto.setAddressId(order.getAddress() != null ? order.getAddress().getAddressId() : null);
        dto.setDeliveryId(order.getDelivery() != null ? order.getDelivery().getDeliveryId() : null);
        dto.setDiscountId(order.getDiscount() != null ? order.getDiscount().getDiscountId() : null);

        // Thông tin đơn hàng
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setUsedPoints(order.getUsedPoints());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setNote(order.getNote());

        // Chi tiết đơn
        List<OrderDetailResponseDTO> detailDTOs = new ArrayList<>();
        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                OrderDetailResponseDTO d = new OrderDetailResponseDTO();
                d.setOrderDetailId(detail.getOrderDetailId());
                d.setVariantId(detail.getVariant().getVariantId());
                d.setProductName(detail.getVariant().getProduct().getProductName());
                d.setQuantity(detail.getQuantity());
                d.setUnitPrice(detail.getUnitPrice());
                d.setSubtotal(detail.getSubtotal());
                detailDTOs.add(d);
            }
        }

        dto.setDetails(detailDTOs);
        return dto;
    }
}
