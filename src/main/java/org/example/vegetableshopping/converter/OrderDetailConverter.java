package org.example.vegetableshopping.converter;

import org.example.vegetableshopping.dto.request.OrderDetailRequest;
import org.example.vegetableshopping.dto.response.OrderDetailResponse;
import org.example.vegetableshopping.entity.Order;
import org.example.vegetableshopping.entity.OrderDetail;
import org.example.vegetableshopping.entity.Product;

public class OrderDetailConverter {

    public static OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .orderDetailId(orderDetail.getOrderDetailId())
                .productId(orderDetail.getProduct().getProductId())
                .orderId(orderDetail.getOrderDetailId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .build();
    }

    public static OrderDetail toOrderDetail(OrderDetailRequest request) {
        OrderDetail orderDetail = new OrderDetail();

        Order order = new Order();
        order.setOrderId(request.getOrderId());
        orderDetail.setOrder(order);

        Product product = new Product();
        product.setProductId(request.getProductId());
        orderDetail.setProduct(product);

        orderDetail.setQuantity(request.getQuantity());
        orderDetail.setPrice(request.getPrice());
        return orderDetail;
    }

}
