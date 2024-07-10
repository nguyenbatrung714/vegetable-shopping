package org.example.vegetableshopping.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.request.OrderRequest;
import org.example.vegetableshopping.dto.response.OrderResponse;
import org.example.vegetableshopping.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderApi {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "15") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getOrders(page, size));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.saveOrder(orderRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable("id") Integer id,
                                                     @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.updateOrder(id, orderRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Integer id) {
        orderService.removeOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getOrder(id));
    }

    @GetMapping({"/{id}/orders"})
    public ResponseEntity<Page<OrderResponse>> getOrdersByUserId(@PathVariable("id") Integer id,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getOrdersByUserId(id, page, size));
    }
}
