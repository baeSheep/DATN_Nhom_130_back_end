package com.DATN.mapper;

import com.DATN.DTO.OrderDetailCreateRequest;
import com.DATN.model.OrderDetail;
import com.DATN.model.ProductVariant;

import java.math.BigDecimal;

public class OrderDetailCreateMapper {

    public static OrderDetail toEntity(OrderDetailCreateRequest req, ProductVariant variant) {
        OrderDetail item = new OrderDetail();

        item.setVariant(variant);
        item.setQuantity(req.getQuantity());
        item.setUnitPrice(req.getUnitPrice());

        // subtotal = unit_price * quantity
        item.setSubtotal(req.getUnitPrice().multiply(
                new BigDecimal(req.getQuantity())
        ));

        return item;
    }
}
