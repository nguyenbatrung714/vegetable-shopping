package org.example.vegetableshopping.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.OrderConverter;
import org.example.vegetableshopping.dto.request.OrderRequest;
import org.example.vegetableshopping.dto.request.PlaceOrderRequest;
import org.example.vegetableshopping.dto.response.OrderResponse;
import org.example.vegetableshopping.entity.Order;
import org.example.vegetableshopping.entity.Product;
import org.example.vegetableshopping.exception.ResourceNotFoundException;
import org.example.vegetableshopping.repository.OrderRepository;
import org.example.vegetableshopping.repository.ProductRepository;
import org.example.vegetableshopping.repository.UserRepository;
import org.example.vegetableshopping.service.OrderDetailService;
import org.example.vegetableshopping.service.OrderService;
import org.example.vegetableshopping.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailService orderDetailService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Override
    public Page<OrderResponse> getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(OrderConverter::toOrderResponse);
    }

    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        userRepository.findById(orderRequest.getUserId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order", "userId", "" + orderRequest.getUserId())
                );

        Order order = new Order();
        OrderConverter.toOrder(order, orderRequest);
        orderRepository.save(order);

        return OrderConverter.toOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Integer id, OrderRequest orderRequest) {
//        userRepository.findById(orderRequest.getUserId())
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("Order", "userId", "" + orderRequest.getUserId())
//                );

        Order order = orderRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order", "id", "" + id)
                );

        OrderConverter.toOrder(order, orderRequest);
        orderRepository.save(order);

        return OrderConverter.toOrderResponse(order);
    }

    @Override
    public void removeOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Order", "id", "" + id);
        }
    }

    @Override
    public OrderResponse getOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", "" + id));

        return OrderConverter.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getOrdersByUserId(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.getOrdersByUser_UserIdOrderByOrderIdDesc(id,pageable);

        return orders.map(OrderConverter::toOrderResponse);
    }

    @Transactional
    @Override
    public PlaceOrderRequest saveOrderTransaction(PlaceOrderRequest placeOrderRequest) {
        OrderResponse orderResponse = saveOrder(placeOrderRequest.getOrderRequest());

        if (placeOrderRequest.getOrderDetailRequests().isEmpty()) {
            throw new IllegalArgumentException("Order null");
        }
        placeOrderRequest.getOrderDetailRequests().forEach(
                item -> {
                    item.setOrderId(orderResponse.getOrderId());
                    orderDetailService.saveOrderDetail(item);
                    int productId = item.getProductId();
                    Product product = productRepository.findById(productId).orElseThrow();
                    int quantity = product.getQuantity();
                    int quantityUpdated = quantity - item.getQuantity();
                    productService.updateQuantity(productId, quantityUpdated);
                }
        );

        return placeOrderRequest;
    }
}
