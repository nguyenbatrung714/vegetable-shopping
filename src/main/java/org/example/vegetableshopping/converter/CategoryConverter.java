package org.example.vegetableshopping.converter;

import org.example.vegetableshopping.dto.request.CategoryRequest;
import org.example.vegetableshopping.dto.response.CategoryResponse;
import org.example.vegetableshopping.entity.Category;

public class CategoryConverter {

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .imagePath(category.getImagePath())
                .build();
    }

    public static Category toCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setImagePath(categoryRequest.getImagePath());
        return category;
    }

    public static void toCategory(Category category, CategoryRequest categoryRequest) {
        if (categoryRequest.getCategoryName() != null) {
            category.setCategoryName(categoryRequest.getCategoryName());
        }
        if (categoryRequest.getImagePath() != null) {
            category.setImagePath(categoryRequest.getImagePath());
        }
    }

}
