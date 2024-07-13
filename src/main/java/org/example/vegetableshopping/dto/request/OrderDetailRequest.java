package org.example.vegetableshopping.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequest {
        private Integer quantity;
        private Double price;
        private Integer productId;
        private Integer orderId;
}
