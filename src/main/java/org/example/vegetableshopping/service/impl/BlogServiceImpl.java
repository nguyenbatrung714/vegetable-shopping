package org.example.vegetableshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.BlogConverter;
import org.example.vegetableshopping.dto.request.BlogRequest;
import org.example.vegetableshopping.dto.response.BlogResponse;
import org.example.vegetableshopping.entity.Blog;
import org.example.vegetableshopping.exception.ResourceNotFoundException;
import org.example.vegetableshopping.repository.BlogRepository;
import org.example.vegetableshopping.service.BlogService;
import org.example.vegetableshopping.service.S3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final S3Service s3Service;

    @Override
    public Page<BlogResponse> getBlogs(int page, int size, String sort) {
        Sort sorting = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            sorting = sort.equalsIgnoreCase("asc")
                    ? Sort.by(Sort.Direction.ASC, "title")
                    : Sort.by(Sort.Direction.DESC, "title");
        }
        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<Blog> blogs = blogRepository.findAll(pageable);

        return blogs.map(blog -> {
            blog.setImagePath(s3Service.generatePresignedUrl(blog.getImagePath()));
            return BlogConverter.toBlogResponse(blog);
        });
    }

    @Override
    public BlogResponse saveBlog(BlogRequest blogRequest, MultipartFile file) {
        return null;
    }

    @Override
    public BlogResponse updateBlog(Integer id, BlogRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public void deleteBlog(Integer id) {

    }

    @Override
    public BlogResponse getBlog(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", "" + id));
        blog.setImagePath(s3Service.generatePresignedUrl(blog.getImagePath()));

        return BlogConverter.toBlogResponse(blog);
    }

    @Override
    public Page<BlogResponse> findBlogByTitle(String title, int page, int size) {
        return null;
    }
}
