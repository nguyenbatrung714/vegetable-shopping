package org.example.vegetableshopping.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.vegetableshopping.enums.OrderStatus;

@Getter
@Setter
public class OrderRequest {
    @NotBlank(message = "Address shipping is required")
    private String addressShipping;

    private OrderStatus orderStatus = OrderStatus.WAIT_FOR_CONFIRMATION;
    private String note;

    @NotNull(message = "Payment method is required")
    private Boolean paymentMethod;

    @NotNull(message = "Payment status is required")
    private Boolean paymentStatus;

    @NotNull(message = "Shipping fee is required")
    private Double shippingFee;

    @NotNull(message = "Total amount is required")
    private Double totalAmount;

    @NotBlank(message = "User id is required")
    private Integer userId;
}
