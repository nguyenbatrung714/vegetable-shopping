package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.request.OrderRequest;
import org.example.vegetableshopping.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<OrderResponse> getOrders(int page, int size);

    OrderResponse saveOrder(OrderRequest orderRequest);

    OrderResponse updateOrder(Integer id, OrderRequest orderRequest);

    void removeOrder(Integer id);

    OrderResponse getOrder(Integer id);

    Page<OrderResponse> getOrdersByUserId(Integer id, int page, int size);

}
