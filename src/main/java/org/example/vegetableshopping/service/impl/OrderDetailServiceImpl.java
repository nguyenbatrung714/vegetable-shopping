package org.example.vegetableshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.OrderDetailConverter;
import org.example.vegetableshopping.dto.request.OrderDetailRequest;
import org.example.vegetableshopping.dto.response.OrderDetailResponse;
import org.example.vegetableshopping.entity.OrderDetail;
import org.example.vegetableshopping.exception.ResourceNotFoundException;
import org.example.vegetableshopping.repository.OrderDetailRepository;
import org.example.vegetableshopping.repository.OrderRepository;
import org.example.vegetableshopping.repository.ProductRepository;
import org.example.vegetableshopping.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderDetailResponse> getOrderDetailByUserId(Integer userId) {
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailsByOrder_OrderId(userId);

        return orderDetails.stream()
                .map(OrderDetailConverter::toOrderDetailResponse)
                .toList();
    }

    @Override
    public OrderDetailResponse saveOrderDetail(OrderDetailRequest request) {
        orderRepository.findById(request.getOrderId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("OrderDetail", "orderId", "" + request.getOrderId())
                );

        productRepository.findById(request.getProductId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("OrderDetail", "productId", "" + request.getOrderId())
                );

        OrderDetail orderDetail = OrderDetailConverter.toOrderDetail(request);
        orderDetailRepository.save(orderDetail);

        return OrderDetailConverter.toOrderDetailResponse(orderDetail);
    }
}
