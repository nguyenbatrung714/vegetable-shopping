package org.example.vegetableshopping.api;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.request.OrderDetailRequest;
import org.example.vegetableshopping.dto.response.OrderDetailResponse;
import org.example.vegetableshopping.service.OrderDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-details")
public class OrderDetailApi {

    private final OrderDetailService orderDetailService;

    @GetMapping("/{id}/order")
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetailsByOrderId(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderDetailService.getOrderDetailByOrderId(id));
    }

    @PostMapping
    public ResponseEntity<OrderDetailResponse> saveOrderDetail(@RequestBody OrderDetailRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderDetailService.saveOrderDetail(request));
    }

}
