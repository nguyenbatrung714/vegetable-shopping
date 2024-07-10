package org.example.vegetableshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.OrderDetailConverter;
import org.example.vegetableshopping.dto.response.OrderDetailResponse;
import org.example.vegetableshopping.entity.OrderDetail;
import org.example.vegetableshopping.repository.OrderDetailRepository;
import org.example.vegetableshopping.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailResponse> getOrderDetailByUserId(Integer userId) {
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailsByOrder_OrderId(userId);

        return orderDetails.stream()
                .map(OrderDetailConverter::toOrderDetailResponse)
                .toList();
    }
}
