package org.example.vegetableshopping.converter;

import org.example.vegetableshopping.dto.request.OrderRequest;
import org.example.vegetableshopping.dto.response.OrderResponse;
import org.example.vegetableshopping.entity.Order;
import org.example.vegetableshopping.entity.User;

public class OrderConverter {

    public static OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .addressShipping(order.getAddressShipping())
                .orderStatus(order.getOrderStatus())
                .note(order.getNote())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .createdDate(order.getCreatedDate())
                .shippingFee(order.getShippingFee())
                .totalAmount(order.getTotalAmount())
                .userId(order.getUser().getUserId())
                .build();
    }

    public static void toOrder(Order order, OrderRequest orderRequest) {
        if (orderRequest.getAddressShipping() != null) {
            order.setAddressShipping(orderRequest.getAddressShipping());
        }
        if (orderRequest.getOrderStatus() != null) {
            order.setOrderStatus(orderRequest.getOrderStatus());
        }
        if (orderRequest.getNote() != null) {
            order.setNote(orderRequest.getNote());
        }
        if (orderRequest.getPaymentMethod() != null) {
            order.setPaymentMethod(orderRequest.getPaymentMethod());
        }
        if (orderRequest.getPaymentStatus() != null) {
            order.setPaymentStatus(orderRequest.getPaymentStatus());
        }
        if (orderRequest.getShippingFee() != null) {
            order.setShippingFee(orderRequest.getShippingFee());
        }
        if (orderRequest.getTotalAmount() != null) {
            order.setTotalAmount(orderRequest.getTotalAmount());
        }
        if (orderRequest.getUserId() != null) {
            User user = new User();
            user.setUserId(orderRequest.getUserId());
            order.setUser(user);
        }
    }

}
