package org.example.vegetableshopping.converter;

import org.example.vegetableshopping.dto.response.BlogResponse;
import org.example.vegetableshopping.entity.Blog;

public class BlogConverter {

    public static BlogResponse toBlogResponse(Blog blog) {
        return BlogResponse.builder()
                .blogId(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .imageUrl(blog.getImagePath())
                .active(blog.getActive())
                .createdDate(blog.getCreatedDate())
                .categoryId(blog.getCategory().getCategoryId())
                .build();
    }

}
