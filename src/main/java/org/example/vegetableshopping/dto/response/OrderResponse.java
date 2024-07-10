package org.example.vegetableshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.vegetableshopping.enums.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Integer orderId;
    private String addressShipping;
    private OrderStatus orderStatus;
    private String note;
    private Boolean paymentMethod;
    private Boolean paymentStatus;
    private LocalDateTime createdDate;
    private Double shippingFee;
    private Double totalAmount;
    private Integer userId;
}
