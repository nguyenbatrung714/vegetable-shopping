package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.request.CategoryRequest;
import org.example.vegetableshopping.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    Page<CategoryResponse> getCategories(int page, int size, String sort);

    CategoryResponse saveCategory(CategoryRequest categoryRequest, MultipartFile multipartFile);

    CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest, MultipartFile multipartFile);

    void removeCategory(Integer categoryId);

    CategoryResponse getCategory(Integer categoryId);

}
