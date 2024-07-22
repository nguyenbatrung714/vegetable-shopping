package org.example.vegetableshopping.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {
    private OrderRequest orderRequest;
    private List<OrderDetailRequest> orderDetailRequests;
}
