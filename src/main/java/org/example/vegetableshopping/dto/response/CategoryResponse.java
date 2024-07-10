package org.example.vegetableshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {
    private Integer categoryId;
    private String categoryName;
    private String imagePath;
}
