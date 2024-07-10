package org.example.vegetableshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailResponse {
    private Integer productId;
    private Integer quantity;
    private Double price;
}
