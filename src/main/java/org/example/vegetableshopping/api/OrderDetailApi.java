package org.example.vegetableshopping.api;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.response.OrderDetailResponse;
import org.example.vegetableshopping.service.OrderDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orderDetails")
public class OrderDetailApi {

    private final OrderDetailService orderDetailService;

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailsByOrderId(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderDetailService.getOrderDetailByUserId(id));
    }

}
