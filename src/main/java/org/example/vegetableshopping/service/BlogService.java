package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.request.BlogRequest;
import org.example.vegetableshopping.dto.response.BlogResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService {

    Page<BlogResponse> getBlogs(int page, int size, String sort);

    BlogResponse saveBlog(BlogRequest blogRequest, MultipartFile file);

    BlogResponse updateBlog(Integer id, BlogRequest request, MultipartFile file);

    void deleteBlog(Integer id);

    BlogResponse getBlog(Integer id);

    Page<BlogResponse> findBlogByTitle(String title, int page, int size);

}
