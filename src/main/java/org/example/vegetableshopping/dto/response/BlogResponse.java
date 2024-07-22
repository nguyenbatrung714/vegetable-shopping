package org.example.vegetableshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BlogResponse {
    private Integer blogId;
    private String title;
    private String content;
    private String imageUrl;
    private Boolean active;
    private LocalDateTime createdDate;
    private Integer categoryId;
}
