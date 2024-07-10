package org.example.vegetableshopping.converter;

import org.example.vegetableshopping.dto.response.OrderDetailResponse;
import org.example.vegetableshopping.entity.OrderDetail;

public class OrderDetailConverter {

    public static OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .productId(orderDetail.getProduct().getProductId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .build();
    }

}
