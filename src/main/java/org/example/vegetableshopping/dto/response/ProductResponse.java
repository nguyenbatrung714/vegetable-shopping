package org.example.vegetableshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double weight;
    private String imagePath;
    private String description;
    private Integer categoryId;
}
