package com.DATN.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DATN.DTO.CheckoutRequest;
import com.DATN.DTO.OrderDTO;
import com.DATN.mapper.OrderMapper;
import com.DATN.model.Address;
import com.DATN.model.Cart;
import com.DATN.model.CartItem;
import com.DATN.model.Discount;
import com.DATN.model.Order;
import com.DATN.model.OrderDetail;
import com.DATN.model.ProductVariant;
import com.DATN.model.Transaction;
import com.DATN.model.Users;
import com.DATN.repository.AddressRepository;
import com.DATN.repository.CartRepository;
import com.DATN.repository.DiscountRepository;
import com.DATN.repository.OrderDetailRepository;
import com.DATN.repository.OrderRepository;
import com.DATN.repository.ProductVariantRepository;
import com.DATN.repository.TransactionRepository;
import com.DATN.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutService {

	private final CartRepository cartRepository;
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final ProductVariantRepository variantRepository;
	private final DiscountRepository discountRepository;
	private final UserRepository userRepository;

	@Transactional
	public OrderDTO processCheckout(CheckoutRequest req) {
	    // 1. Lấy user
	    Users user = userRepository.findById(req.getUserId())
	            .orElseThrow(() -> new RuntimeException("User không tồn tại"));

	    // 2. Lấy giỏ hàng của user
	    Cart cart = cartRepository.findByUser_UserID(user.getUserID())
	            .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));

	    // 3. Lấy các CartItem
	    List<CartItem> cartItems = cart.getItems();
	    if (cartItems.isEmpty()) throw new RuntimeException("Giỏ hàng trống");

	    // 4. Kiểm tra tồn kho & tính subtotal
	    BigDecimal subtotal = BigDecimal.ZERO;
	    for (CartItem item : cartItems) {
	        ProductVariant variant = item.getVariant();

	        if (item.getQuantity() <= 0)
	            throw new RuntimeException("Số lượng không hợp lệ");

	        if (variant.getStockQuantity() < item.getQuantity()) {
	            throw new RuntimeException(
	                    variant.getProduct().getProductName() + " không đủ tồn kho"
	            );
	        }

	        subtotal = subtotal.add(
	                variant.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
	        );
	    }

	    // 5. Áp dụng mã giảm giá (nếu có)
	    Discount discount = null;
	    BigDecimal discountValue = BigDecimal.ZERO;

	    if(req.getCouponCode() != null && !req.getCouponCode().isEmpty()){
	        discount = discountRepository.findByCodeAndActiveTrue(req.getCouponCode()).orElse(null);
	        if(discount == null){
	            discountValue = BigDecimal.ZERO; // Không áp dụng
	        } else {
	            discountValue = discount.getDiscountValue();
	        }
	    }


	    // 6. Tính phí vận chuyển
	    BigDecimal shippingFee = BigDecimal.valueOf(30000); // Mặc định 30k
	    // Nếu muốn dựa trên tỉnh/thành phố, có thể thêm logic ở đây

	    // 7. Tính tổng đơn hàng cuối
	    BigDecimal finalAmount = subtotal.add(shippingFee).subtract(discountValue);

	    // 8. Tạo Order
	    Order order = new Order();
	    order.setUsers(user);
	    order.setTotalAmount(finalAmount);
	    order.setDeliveryFee(shippingFee);
	    order.setStatus("PENDING");
	    order.setPaymentMethod(req.getPaymentMethod());
	    order.setDiscount(discount);
	    order = orderRepository.save(order);

	 // 9. Tạo OrderDetail và trừ tồn kho
	    for (CartItem item : cartItems) {
	        OrderDetail detail = new OrderDetail();
	        detail.setOrder(order);
	        detail.setVariant(item.getVariant());
	        detail.setQuantity(item.getQuantity());
	        detail.setUnitPrice(item.getUnitPrice());
	        detail.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
	        orderDetailRepository.save(detail);

	        // Thêm vào danh sách details của Order
	        order.getDetails().add(detail);

	        // Trừ tồn kho
	        ProductVariant variant = item.getVariant();
	        variant.setStockQuantity(variant.getStockQuantity() - item.getQuantity());
	    }


	    // 10. Xoá giỏ hàng sau khi đặt hàng thành công
	    cart.getItems().clear();
	    cartRepository.save(cart);

	    // 11. Trả về DTO
	    return OrderMapper.toDTO(order);
	}

}


