package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.request.OrderDetailRequest;
import org.example.vegetableshopping.dto.response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailResponse> getOrderDetailByUserId(Integer userId);

    OrderDetailResponse saveOrderDetail(OrderDetailRequest request);

}
